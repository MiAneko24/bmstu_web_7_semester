
create domain system_type as text
not null check (
	value = 'Sugeno' or value = 'Mamdani'
);

create domain specialization_type as text
not null check
(
	value = 'physics' or value = 'chemistry' or value = 'informatics'
);

create table jobs(
	j_id uuid primary key,
	variable_values jsonb,
	s_id int
);

--grant connect on postgres to user_role;

create table system (
	s_id int primary key,
	s_name text unique,
	s_type system_type not null,
	specialization specialization_type
);


create table variable (
	v_id int primary key,
	v_name text not null,
	min_value real not null,
	max_value real not null,
	v_value real null check(v_value between min_value and max_value),
	s_id int not null references system(s_id) on delete cascade
);


create domain m_function_type as text
not null check
(
	value = 'trapezoidal' or value = 'triangle' or value = 'shoulder'
	or value = 'linguistic' or value = 'gauss' or value = 'crisp'
	or value = 'linear'
);

create domain linguistic_barrier as text
check
(
	value = 'Very' or value = 'More or less' or value = 'Plus' or value = 'Not'
	or value = 'Not very' or value is null
);

create table membership_function
(
	m_id int primary key,
	term text check (term is not null or m_type = 'linear' or m_type = 'crisp'),
	m_type m_function_type,
	v_id int references variable(v_id) on delete cascade check((m_type = 'linear' and v_id is not null) or (m_type != 'linear')),
	parameter1 real check (m_type != 'linguistic' or null),
	parameter2 real check (((m_type = 'trapezoidal' or m_type = 'triangle' or m_type = 'shoulder')
		and parameter2 > parameter1) or m_type = 'gauss' or null),
	parameter3 real check (((m_type = 'trapezoidal' or m_type = 'triangle')
		and parameter3 > parameter2) or m_type = 'shoulder' or null),
	parameter4 real check (m_type = 'trapezoidal' and parameter4 > parameter3 or null),
	m_value real check (m_value between 0 and 1 or m_type = 'linear' or m_type = 'crisp'),
	p_id int references membership_function(m_id) on delete cascade,
	barrier linguistic_barrier check ((m_type = 'lingustic' and barrier is not null) or null),
	is_active boolean default true
);

create domain ant_connection_type as text
check
(
	value = 'or' or value = 'and'
);

create table rule (
	r_id int primary key,
	s_id int references system(s_id) on delete cascade,
	antecedent_connection ant_connection_type,
	weight real not null default 1
);

create table antecedent (
	a_id int primary key,
	m_id int references membership_function(m_id) on delete cascade
);

create table consequent (
	c_id int primary key,
	m_id int references membership_function(m_id) on delete cascade,
	r_id int references rule(r_id) on delete cascade,
	v_id int references variable(v_id) on delete cascade
);

create table rule_antecedents (
	r_id int references rule(r_id) on delete cascade,
	a_id int references antecedent(a_id) on delete cascade,
	primary key (r_id, a_id)
);

create or replace function trapezoidal(value real, a real, b real, c real, d real)
returns real
as $$
declare
	res real;
begin
	if (value between a and b)
	then
		res := (value - a) / (b - a);
	else
		if (value between b and c)
		then res := 1;
		else
			if (value between c and d)
			then
				res := (d - value) / (d - c);
			else
				res := 0;
			end if;
		end if;
	end if;
	return res;
end;
$$ language plpgsql;


create or replace function triangle(value real, a real, b real, c real)
returns real
as $$
declare
	res real;
begin
	if (value between a and b)
	then
		res := (value - a) / (b - a);
	else
		if (value between b and c)
		then
			res := (c - value) / (c - b);
		else
			res := 0;
		end if;
	end if;
	return res;
end;
$$ language plpgsql;


create or replace function shoulder(value real, a real, b real, g real)
returns real
as $$
declare
	res real;
	rev bool;
begin
	if (g < a)
		then select 1 - shoulder from shoulder(value, g, b, a) into res;
	else
		if (value <= a)
		then
			res := 0;
		else
			if (value <= b)
			then
				res := 2 * ((value - a) / (g - a)) ^ 2;
			else
				if (value <= g)
				then
					res := 1 - 2 * ((value - g) / (g - a)) ^ 2;
				else
					res := 1;
				end if;
			end if;
		end if;
--	if (rev)
--		then
--			res := 1 - res;
	end if;
	return res;
end
$$ language plpgsql;

create or replace function gauss(value real, g real, b real)
returns real
as $$
declare
	res real;
begin
	if (value <= g)
	then
		select * from shoulder(value, g - b, (g - b / 2)::real, g) into res;
	else
		select * from shoulder(value, g, (g + b / 2)::real, g + b) into res;
		res := 1 - res;
	end if;
	return res;
end
$$ language plpgsql;

select * from gauss(26::real, 6::real, 21::real);

create or replace function linguistic(v real, barrier linguistic_barrier, pid int)
returns real
as $$
declare
	res real;
begin
	select case m_type
		when 'trapezoidal' then (select * from trapezoidal(v, parameter1, parameter2, parameter3, parameter4))
		when 'triangle' then (select * from triangle(v, parameter1, parameter2, parameter3))
		when 'shoulder' then (select * from shoulder(v, parameter1, parameter2, parameter3))
		when 'gauss' then (select * from gauss(v, parameter1, parameter2))
		when 'crisp' then parameter1
		when 'linguistic' then (select mf2.m_value from membership_function mf2 where mf2.m_id = pid)
		end
	from membership_function mf1
	where m_id = pid
	into res;
	select case barrier
		when 'Very'
			then res ^ 2
		when 'More or less'
			then res ^ 0.5
		when 'Plus'
			then res ^ 1.25
		when 'Not'
			then 1 - res
		when 'Not very'
			then 1 - res ^ 2
		else 0
		end
	into res;
	return res;
end;
$$ language plpgsql;

--
create or replace function update_membership()
returns trigger
as $$
begin
	update membership_function
	set m_value = case m_type
				when 'trapezoidal' then (select * from trapezoidal(new.v_value, parameter1, parameter2, parameter3, parameter4))
				when 'triangle' then (select * from triangle(new.v_value, parameter1, parameter2, parameter3))
				when 'shoulder' then (select * from shoulder(new.v_value, parameter1, parameter2, parameter3))
				when 'gauss' then (select * from gauss(new.v_value, parameter1, parameter2))
				when 'linguistic' then (select * from linguistic(new.v_value, barrier, p_id))
				when 'linear' then parameter1 * new.v_value
		end
	where v_id = new.v_id;
	return new;
end
$$ language plpgsql;

drop trigger if exists update_funcs on variable ;
create trigger update_funcs
after update on variable
for row execute procedure update_membership();

create or replace function membership_func_check()
returns trigger
as $$
begin
	if (new.m_type = 'crisp')
	then
		new.m_value := new.parameter1;
	end if;
	return new;
end
$$ language plpgsql;

create trigger insert_mf
before insert on membership_function
for row execute procedure membership_func_check();



insert into system
values(1, 'Контроль температуры', 'Mamdani', 'physics');

insert into variable values
(1, 'Temperature', -60, 70, null, 1),
(2, 'Pressure', 0, 100, null, 1);
insert into membership_function values
--(1, 'Not Cold', 'shoulder', 'Temperature', -20, -7, 5, null, null, null, null),
(2, 'Cold', 'shoulder', 1, 5, -7.5, -20, null, null, null, null),
(3, 'Very cold', 'linguistic', 1, null, null, null, null, null, 2, 'Very'),
(4, 'Neutral', 'gauss', 1, 15, 10, null, null, null, null, null),
(5, 'Warm', 'trapezoidal', 1, 7, 20, 26, 30, null, null, null),
(6, 'A bit hot', 'triangle', 1, 24, 28, 32, null, null, null, null),
(7, 'Hot', 'shoulder', 1, 25, 32.5, 40, null, null, null, null),
(9, 'More or less hot', 'linguistic', 1, null, null, null, null, null, 7, 'More or less'),
(10, 'Not very warm', 'linguistic', 1, null, null, null, null, null, 5, 'Not very'),
(11, 'Plus neutral', 'linguistic', 1, null, null, null, null, null, 4, 'Plus'),
(12, 'Cons', 'linear', 1, 0.8, null, null, null, null, null, null);
insert into membership_function values
(13, 'Cons', 'crisp', null, 9.2);

update variable set v_value = 9 where v_name = 'Temperature';



insert into "system" values
(3, 'Laboratory work', 'Sugeno', 'informatics');

delete from variable where v_name = 's2';
insert into variable values
(10, 'a1', 0, 100, null, 3),
(11, 'a2', 100, 200, null, 3),
(12, 's1', 0, 500, null, 3);

insert into membership_function values
(20, 'a11', 'triangle', 10, 10, 30, 60),
(21, 'a12', 'triangle', 10, 30, 60, 80),
(22, 'a21', 'triangle', 11, 110, 120, 170),
(23, 'a22', 'triangle', 11, 120, 170, 180),
(24, null, 'linear', 10, 0.04315, null, null),
(25, null, 'linear', 11, -0.3974, null, null),
(26, null, 'crisp', null, 307.567, null, null),
(27, null, 'linear', 10, 0.25253, null, null),
(28, null, 'linear', 11, -0.16091, null, null),
(29, null, 'crisp', null, 303.608, null, null);

update membership_function
set parameter1 = 0.16091
where m_id = 28;

insert into "rule" values
(10, 3, 'and', 1),
(11, 3, 'and', 1);

insert into antecedent
values
(10, 20),
(11, 22),
(12, 21),
(13, 23);

insert into rule_antecedents
values
(10, 10),
(10, 11),
(11, 12),
(11, 13);
--
--update consequent
--set v_name = 's1'
--where v_name = 's2';

insert into consequent values
(10, 24, 10, 12),
(11, 25, 10, 12),
(12, 26, 10, 12),
(13, 27, 11, 12),
(14, 28, 11, 12),
(15, 29, 11, 12);

update variable
set v_value = 55
where v_name ='a1';

update variable
set v_value = 130
where v_name = 'a2';

------------------------------


insert into "system"
values
(4, 'Accumulators', 'Sugeno', 'physics');

insert into variable values
(20, 'in1', -62.652, -12.365187, null, 4),
(21, 'in2', 1.95734, 99.9969, null, 4),
(22, 'out1', 2.5081366, 3.527538, null, 4);


insert into membership_function values
(30, 'in1cluster1', 'gauss', 20, -12.538318, 16.0191136),
(31, 'in1cluster2', 'gauss', 20, -12.693450, 16.64626),
(32, 'in1cluster3', 'gauss', 20, -25.042453, 16.01828),
(33, 'in1cluster4', 'gauss', 20, -62.62041, 16.007),
(34, 'in1cluster5', 'gauss', 20, -24.92186, 15.328027),
(35, 'in2cluster1', 'gauss', 21, 63.77841, 13.96134),
(36, 'in2cluster2', 'gauss', 21, 27.59403, 12.759025),
(37, 'in2cluster3', 'gauss', 21, 91.245513, 13.889254),
(38, 'in2cluster4', 'gauss', 21, 57.84007, 14.074748),
(39, 'in2cluster5', 'gauss', 21, 16.981307, 14.818675),
(40, 'out1cluster1', 'linear', 20, 0.00249216, null),
(41, 'out1cluster1', 'linear', 21, 0.0035852096, null),
(42, 'out1cluster1', 'crisp', null, 3.05265048, null),
(43, 'out1cluster2', 'linear', 20, 0.060665, null),
(44, 'out1cluster2', 'linear', 21, 0.002954407, null),
(45, 'out1cluster2', 'crisp', null, 2.9345918, null),
(46, 'out1cluster3', 'linear', 20, 0.0018589, null),
(47, 'out1cluster3', 'linear', 21, 0.005227, null),
(48, 'out1cluster3', 'crisp', null, 2.8266412, null),
(49, 'out1cluster4', 'linear', 20, 0.00205711, null),
(50, 'out1cluster4', 'linear', 21, 0.004130177, null),
(51, 'out1cluster4', 'crisp', null, 2.97927264, null),
(52, 'out1cluster5', 'linear', 20, 0.0192987, null),
(53, 'out1cluster5', 'linear', 21, 0.08263298, null),
(54, 'out1cluster5', 'crisp', null, 2.85392466, null);

insert into "rule" values
(20, 4, 'and', 1),
(21, 4, 'and', 1),
(22, 4, 'and', 1),
(23, 4, 'and', 1),
(24, 4, 'and', 1);


insert into antecedent
values
(20, 30),
(21, 31),
(22, 32),
(23, 33),
(24, 34),
(25, 35),
(26, 36),
(27, 37),
(28, 38),
(29, 39);

insert into rule_antecedents
values
(20, 20),
(20, 25),
(21, 21),
(21, 26),
(22, 22),
(22, 27),
(23, 23),
(23, 28),
(24, 24),
(24, 29);




insert into consequent values
(20, 40, 20, 22),
(21, 41, 20, 22),
(22, 42, 20, 22),
(23, 43, 21, 22),
(24, 44, 21, 22),
(25, 45, 21, 22),
(26, 46, 22, 22),
(27, 47, 22, 22),
(28, 48, 22, 22),
(29, 49, 23, 22),
(30, 50, 23, 22),
(31, 51, 23, 22),
(32, 52, 24, 22),
(33, 53, 24, 22),
(34, 54, 24, 22);


update variable
set v_value = -50.33632
where v_name ='in1';

update variable
set v_value = 67.382009
where v_name = 'in2';



------------------------------------------------------

insert into "system" values
(5, 'Accidents', 'Mamdani', 'informatics');

insert into variable values
(30, 'Age', 0, 100, null, 5),
(31, 'Accident probability', 0, 1, null, 5);

insert into "rule" values
(30, 5, 'and', 1),
(31, 5, 'and', 1);

insert into membership_function values
(60, 'Young', 'shoulder', 30, 36, 29, 22),
(61, 'Middle', 'gauss', 30, 40, 24, null),
(62, 'Very old', 'shoulder', 30, 51, 64, 77),
(63, 'Low', 'shoulder', 31, 0.8, 0.4, 0),
(64, 'High', 'shoulder', 31, 0.2, 0.6, 1);

insert into antecedent values
(30, 60),
(31, 61);

insert into rule_antecedents values
(30, 30),
(31, 31);


insert into consequent values
(40, 63, 31),
(41, 64, 30);

update variable set v_value = 28 where v_name ='Age';

-----------------------------------------------------

create or replace function trapezoidal_get_start(degree real, a real, b real, c real, d real)
returns real
as $$
declare
	res real;
	tmp_res real;
begin
	if (abs(degree) < 1e-5)
		then
			res := a;
	else
		if (abs(degree - 1) < 1e-5)
			then
				res := b;
		else
			res := degree * (b - a) + a;
		end if;
	end if;
	return res;
end
$$ language plpgsql;


create or replace function trapezoidal_get_end(degree real, a real, b real, c real, d real)
returns real
as $$
declare
	res real;
begin
	if (abs(degree) < 1e-5)
		then
			res := d;
	else
		if (abs(degree - 1) < 1e-5)
			then
				res := c;
		else
			res := d - degree * (d - c);
		end if;
	end if;
	return res;
end
$$ language plpgsql;

create or replace function triangle_get_start(degree real, a real, b real, c real)
returns real
as $$
declare
	res real;
begin
	if (abs(degree) < 1e-5)
	then
		res := a;
	else
		res := degree * (b - a) + a;
	end if;
	return res;
end
$$ language plpgsql;


create or replace function triangle_get_end(degree real, a real, b real, c real)
returns real
as $$
declare
	res real;
begin
	if (abs(degree) < 1e-5)
	then
		res := c;
	else
		res := c - degree * (c - b);
	end if;
	return res;
end
$$ language plpgsql;

create or replace function shoulder_get_start(degree real, a real, b real, g real, var int)
returns real
as $$
declare
	res real;
	tmp_res real;
begin
	select min_value from variable where v_id = var into res;
	if (a < g)
		then begin
			if (abs(degree) < 1e-5)
				then tmp_res := a;
			else
				if (abs(degree - 1) < 1e-5)
					then tmp_res := g;
				else
					if (degree <= 0.5)
						then tmp_res := sqrt(degree / 2) * (g - a) + a;
					else
						tmp_res := g - (g - a) * sqrt((1 - degree) / 2);
					end if;
				end if;
			end if;
			if (tmp_res > res)
				then res := tmp_res;
			end if;
		end;
	end if;
	return res;
end;
$$ language plpgsql;



create or replace function shoulder_get_end(degree real, a real, b real, g real, var int)
returns real
as $$
declare
	res real;
begin
	if (a > g)
		then select shoulder_get_start((1 - degree)::real, g, b, a, var) into res;
	else
		select max_value from variable where v_id = var into res;
	end if;
	return res;
end;
$$ language plpgsql;


--drop function gauss_get_start;
create or replace function gauss_get_start(degree real, g real, b real, var int)
returns real
as $$
begin
	return (select * from shoulder_get_start(degree::real, g - b, (g - b/2)::real, g, var));
end;
$$ language plpgsql;


create or replace function gauss_get_end(degree real, g real, b real, var int)
returns real
as $$
begin
	return (select * from shoulder_get_end((1 - degree)::real, g + b, (g + b/2)::real, g, var));
end
$$ language plpgsql;

create or replace function linguistic_get_start(degree real, barrier linguistic_barrier, pid int)
returns real
as $$
declare
	res real;
begin
	select case barrier
		when 'Very'
			then degree ^ 0.5
		when 'More or less'
			then degree ^ 2
		when 'Plus'
			then degree ^ (4/5)
		when 'Not'
			then 1 - degree -- x = 1 - y^2
		when 'Not very'
			then (1 - degree) ^ 0.5
		end
	into res;
	return (
		select case m_type
			when 'trapezoidal' then (select * from trapezoidal_get_start(res, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_start(res, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_start(res, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_start(res, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_start(res, barrier, p_id))
			end
		from membership_function
		where m_id = pid);
end
$$ language plpgsql;

create or replace function linguistic_get_end(degree real, barrier linguistic_barrier, pid int)
returns real
as $$
declare
	res real;
begin
	select case barrier
		when 'Very'
			then degree ^ 0.5
		when 'More or less'
			then degree ^ 2
		when 'Plus'
			then degree ^ (4/5)
		when 'Not'
			then 1 - degree -- x = 1 - y^2
		when 'Not very'
			then (1 - degree) ^ 0.5
		end
	into res;
	return (
		select case m_type
			when 'trapezoidal' then (select * from trapezoidal_get_end(res, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_end(res, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_end(res, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_end(res, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_end(res, barrier, p_id))
			end
		from membership_function
		where m_id = pid);
end
$$ language plpgsql;

create or replace function memb_func_get_start(degree real, mf_id int)
returns real
as $$
begin
	return (
		select case m_type
			when 'trapezoidal' then (select * from trapezoidal_get_start(degree, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_start(degree, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_start(degree, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_start(degree, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_start(degree, barrier, p_id))
			end
		from membership_function mf
		where m_id = mf_id);
end
$$ language plpgsql;

create or replace function memb_func_get_end(degree real, mf_id int)
returns real
as $$
begin
	return (
		select case m_type
			when 'trapezoidal' then (select * from trapezoidal_get_end(degree, parameter1, parameter2, parameter3, parameter4))
			when 'triangle' then (select * from triangle_get_end(degree, parameter1, parameter2, parameter3))
			when 'shoulder' then (select * from shoulder_get_end(degree, parameter1, parameter2, parameter3, v_id))
			when 'gauss' then (select * from gauss_get_end(degree, parameter1, parameter2, v_id))
			when 'linguistic' then (select * from linguistic_get_end(degree, barrier, p_id))
			end
		from membership_function mf
		where m_id = mf_id);
end
$$ language plpgsql;
-------------------------------------------------


insert into "system" values
(2, 'Контроль заряда LI-ion батареек', 'Sugeno', 'physics');

insert into "rule" values
(1, 1, 'or', 0.2);
insert into "rule"  values
(2, 1, 'and');
insert into "rule" values
(3,1, 'and', 0.9);


delete from rule_antecedents
where a_id = 2;

update variable
set v_value = 63
where v_name = 'Pressure';

insert into "rule" values
(4, 2, 'and', 0.2),
(5, 2, 'or', 0.92);


insert into variable values
(3, 'Volume', 15, 90, null, 1);

insert into membership_function values
(14, 'Cons', 'linear', 1, 0.72, null, null),
(15, 'High', 'shoulder', 2, 50, 65, 80),
(16, 'Cons', 'linear', 2, 0.55, null, null),
(17, 'Cons', 'crisp', null, 0.22, null, null);


insert into antecedent
values
(1, 2),
(2, 3),
(3, 5),
(4, 7),
(5, 10),
(6, 9),
(7, 15);

select * from "rule" r ;

insert into rule_antecedents values
(1, 2),
(1, 4),
(1, 6),
(2, 1),
(2, 2),
(2, 5),
(2, 6),
(3, 2),
(3, 3),
(3, 5);

update membership_function
set parameter1 = 12
where m_id = 2;

update antecedent
set m_id = 11
where a_id = 6;

insert into rule_antecedents values
(4, 6),
(5, 1),
(5, 5),
(5, 7);

insert into consequent values
(1, 12, 3, 2),
(2, 17, 3, 2),
(3, 14, 4, 2),
(4, 14, 5, 3),
(5, 13, 5, 3),
(6, 16, 5, 3);

create or replace function count_antecedents(sys_id int)
returns table (
	r_id int,
	ant_value real
)
as $$
begin
	return query (select distinct r.r_id as "rule_id", case
				when exists(select * from rule_antecedents ra1
								join antecedent a1 on ra1.a_id  = a1.a_id
								join membership_function mf1 on mf1.m_id = a1.m_id
							where ra1.r_id = r.r_id and not mf1.is_active)
							then 0.0
				when r.antecedent_connection = 'or' then (select max(mf1.m_value) * r.weight
							from rule_antecedents ra1
								join antecedent a1 on ra1.a_id  = a1.a_id
								join membership_function mf1 on mf1.m_id = a1.m_id
							where ra1.r_id = r.r_id)
				when r.antecedent_connection = 'and' then (select min(mf1.m_value) * r.weight
							from rule_antecedents ra1
								join antecedent a1 on ra1.a_id  = a1.a_id
								join membership_function mf1 on mf1.m_id = a1.m_id
								where ra1.r_id = r.r_id)
				end as "value"
	from system s
			join rule r on s.s_id = r.s_id
			join rule_antecedents ra on r.r_id = ra.r_id
			join antecedent a on ra.a_id  = a.a_id
			join membership_function mf on mf.m_id = a.m_id
		where s.s_id = sys_id);
end;
$$ language plpgsql;


create or replace function get_output_Sugeno(s_id int)
returns table (
	r_id int,
	consequent int,
	value real,
	weight real
)
as $$
begin
	return query
		(select q.r_id, q.consequent, sum(q.m_value) as "value", q.weight::real as "weight"
		from
			(select distinct a.r_id, c.v_id as "consequent", mf.m_value, a.ant_value as "weight"
			from (select * from count_antecedents(s_id)) a
				join consequent c on a.r_id = c.r_id
				join membership_function mf on c.m_id = mf.m_id
			where a.ant_value != 0 and mf.is_active) q
		group by q.r_id, q.consequent, q.weight);
end
$$ language plpgsql;

insert into membership_function values
(18, 'Normal', 'gauss', 3, 60, 20, null),
(19, 'Hight', 'shoulder', 3, 68, 74, 80);


insert into consequent values
(7, 18, 1),
(8, 19, 2);

update variable
set v_value = 55
where v_name = 'Volume';


create or replace function get_result_Sugeno(s_id int)
returns table (
	v_id int,
	value real
)
as $$
begin
	return query
	(select q.consequent, sum(q.weight * q.value) / sum(q.weight) as "value"
		from get_output_Sugeno(s_id) q
		group by q.consequent
		);
end
$$ language plpgsql;

create or replace function get_fuzzy_Mamdani_output(s_id int)
returns table (
	v_id int,
	value real,
	cent real
)
as $$
begin
	return query
	(
	with intervals as (
	select q.r_id, q.v_id, q.m_id, q.value,
							memb_func_get_start(q.value, q.m_id) as "start",
							memb_func_get_end(q.value, q.m_id) as "end"
	from
		(select c.r_id, mf.v_id, mf.m_id, case r.antecedent_connection
							when 'or' then case
										when max(ant_value) > max(mf.m_value) or mf.m_value is null
											then --'max ant'
												max(ant_value)
										else --'max mf'
											max(mf.m_value)
										end
							else case
								when min(ant_value) < min(mf.m_value) or mf.m_value is null
									then --'min ant'
										min(ant_value)
								else --'min mf'
									min(mf.m_value)
								end
							end as "value"
			from count_antecedents(s_id) a
			join "rule" r  on a.r_id = r.r_id
			join consequent c on c.r_id = r.r_id
			join membership_function mf on c.m_id = mf.m_id
			where mf.is_active
			group by c.r_id, mf.v_id, mf.m_id, r.antecedent_connection) q
	)
	select q1.v_id, q1.value, ((q1.start + q1.end) / 2)::real as "cent"
	from
		(select q.v_id, q.value, case
						when (select max("end")
								from intervals i
								where i.value > q.value and i.end < q.end) > q.start
								then (select max("end")
								from intervals i
								where i.value > q.value and i.end < q.end)
						else q.start
						end as "start",
						case
						when (select min("start")
								from intervals i
								where i.value > q.value and i.start > q.start) < q.end
								then (select min("start")
								from intervals i
								where i.value > q.value and i.start > q.start)
						else q.end
						end as "end"
							--max(q.value) as "value"
		from intervals q) q1);
end;
$$ language plpgsql;


create or replace function get_result_Mamdani(s_id int)
returns table (
	v_id int,
	value real
)
as $$
begin
	return query
	(select q.v_id, case sum(q.value)
					when 0 then 0
					else sum(q.value * q.cent) / sum(q.value)
					end as "value"
		from get_fuzzy_Mamdani_output(s_id) q
		group by q.v_id
		);
end
$$ language plpgsql;

create or replace function get_output(sys_id int)
returns table(
	var_name text,
	value real
)
security definer
as $$
declare
	t text;
begin
	drop table if exists tmp;
	create temp table tmp
	(
		v_id int,
		value real
	);
	select s_type
	from system s
	where s.s_id = sys_id
	into t;
	if (t = 'Sugeno') then
		--return query (
		insert into tmp select * from get_result_Sugeno(sys_id);-- into tmp;
	else
--		return query (
		insert into tmp select * from get_result_Mamdani(sys_id);-- into tmp;
	end if;
--	update variable set v_value = (select t.value from tmp t where t.v_id = v_id)
--	where v_id in (select t.v_id from tmp t) and s_id = sys_id;
	return query (select v_name, t.value
			from tmp t join variable v on t.v_id = v.v_id);
end;
$$ language plpgsql;

----------------------------------------

revoke all on all tables in schema public from public;

revoke all on database postgres from public;

revoke all on schema public from public;

create role administrator superuser;
create user administr inherit
login
password 'secret_password';

grant administrator to administr;


create role user_role;

grant connect on database postgres to user_role;
grant usage on schema pg_catalog to user_role;
revoke all on schema public from user_role;
set search_path to public;

grant usage on schema public to user_role;
revoke all on all tables in schema public from user_role;
grant select on all tables in schema public to user_role;
revoke all on all functions in schema public from user_role;

grant execute on function get_output(int) to user_role;
create or replace procedure set_variable_value(var_id int, value real)
security definer
as $$
begin
	update variable set v_value = value where v_id = var_id;
end
$$ language plpgsql;
grant execute on procedure set_variable_value(int, real) to user_role;

create or replace function get_roles()
returns table(
	usr_role name)
as $$
begin
	return query
		select rolname from pg_roles where pg_has_role((select current_user), oid, 'member');
end;
$$ language plpgsql;

grant select on table pg_roles to user_role;

grant execute on function get_roles() to user_role;

create user usr inherit
login
password '123';

select * from "system" s ;

grant user_role to usr;

create role expert inherit;

grant select on all tables in schema pg_catalog to administr;
----------------PHYSICS EXPERTS VIEWS--------------------

drop view if exists physics_expert_rule_antecedents;
drop view if exists physics_expert_antecedents;
drop view if exists physics_expert_consequents;
drop view if exists physics_expert_membership_functions;
drop view if exists physics_expert_rules;
drop view if exists physics_expert_variables;
drop view if exists physics_expert_systems;

create view physics_expert_systems as
	select *
	from system
	where specialization = 'physics'
with cascaded check option;

create view physics_expert_variables as
	select *
	from variable
	where s_id in (
		select s_id from physics_expert_systems)
with cascaded check option;

create view physics_expert_membership_functions as
	select *
	from membership_function
	where v_id in (select v_id from physics_expert_variables)
with cascaded check option;

create view physics_expert_rules as
	select *
	from rule
	where s_id in (select s_id from physics_expert_systems)
with cascaded check option;

create view physics_expert_antecedents as
	select * from antecedent a
	where m_id in (select m_id from physics_expert_membership_functions)
with cascaded check option;

create view physics_expert_consequents as
	select * from consequent c
	where r_id in (select r_id from physics_expert_rules)
		and m_id in (select m_id from physics_expert_membership_functions)
		and (v_id is null or v_id in (select v_id from physics_expert_variables))
with cascaded check option;

create view physics_expert_rule_antecedents as
	select * from rule_antecedents ra
	where r_id in (select r_id from physics_expert_rules)
		and a_id in (select a_id from physics_expert_antecedents)
with cascaded check option;

-----------------------------------------------------

grant user_role to expert;
create role physics inherit;
create role chemistry inherit;
create role informatics inherit;

grant expert to physics;
grant expert to chemistry;
grant expert to informatics;


grant all on physics_expert_variables to physics;
grant all on physics_expert_rule_antecedents to physics;
grant all on physics_expert_antecedents to physics;
grant all on physics_expert_consequents to physics;
grant all on physics_expert_membership_functions to physics;
grant all on physics_expert_rules to physics;
grant all on physics_expert_systems to physics;


----------------CHEMISTRY EXPERTS VIEWS--------------------
drop view if exists chemistry_expert_rule_antecedents;
drop view if exists chemistry_expert_antecedents;
drop view if exists chemistry_expert_consequents;
drop view if exists chemistry_expert_membership_functions;
drop view if exists chemistry_expert_rules;
drop view if exists chemistry_expert_variables;
drop view if exists chemistry_expert_systems;


create view chemistry_expert_systems as
	select *
	from system
	where specialization = 'chemistry'
with cascaded check option;

create view chemistry_expert_variables as
	select *
	from variable
	where s_id in (
		select s_id from chemistry_expert_systems)
with cascaded check option;

create view chemistry_expert_membership_functions as
	select *
	from membership_function
	where v_id in (select v_id from chemistry_expert_variables)
with cascaded check option;

create view chemistry_expert_rules as
	select *
	from rule
	where s_id in (select s_id from chemistry_expert_systems)
with cascaded check option;

create view chemistry_expert_antecedents as
	select * from antecedent a
	where m_id in (select m_id from chemistry_expert_membership_functions)
with cascaded check option;

create view chemistry_expert_consequents as
	select * from consequent c
	where r_id in (select r_id from chemistry_expert_rules)
		and m_id in (select m_id from chemistry_expert_membership_functions)
		and (v_id is null or v_id in (select v_id from chemistry_expert_variables))
with cascaded check option;

create view chemistry_expert_rule_antecedents as
	select * from rule_antecedents ra
	where r_id in (select r_id from chemistry_expert_rules)
		and a_id in (select a_id from chemistry_expert_antecedents)
with cascaded check option;

-----------------------------------------------------

grant all on chemistry_expert_variables to chemistry;
grant all on chemistry_expert_antecedents to chemistry;
grant all on chemistry_expert_rule_antecedents to chemistry;
grant all on chemistry_expert_consequents to chemistry;
grant all on chemistry_expert_membership_functions to chemistry;
grant all on chemistry_expert_rules to chemistry;
grant all on chemistry_expert_systems to chemistry;

----------------INFORMATICS EXPERTS VIEWS--------------------
drop view if exists informatics_expert_rule_antecedents;
drop view if exists informatics_expert_antecedents;
drop view if exists informatics_expert_consequents;
drop view if exists informatics_expert_membership_functions;
drop view if exists informatics_expert_rules;
drop view if exists informatics_expert_variables;
drop view if exists informatics_expert_systems;

create view informatics_expert_systems as
	select *
	from system
	where specialization = 'informatics'
with cascaded check option;

create view informatics_expert_variables as
	select *
	from variable
	where s_id in (
		select s_id from informatics_expert_systems)
with cascaded check option;

create view informatics_expert_membership_functions as
	select *
	from membership_function
	where v_id in (select v_id from informatics_expert_variables)
with cascaded check option;

create view informatics_expert_rules as
	select *
	from rule
	where s_id in (select s_id from informatics_expert_systems)
with cascaded check option;

create view informatics_expert_antecedents as
	select * from antecedent a
	where m_id in (select m_id from informatics_expert_membership_functions)
with cascaded check option;

create view informatics_expert_consequents as
	select * from consequent c
	where r_id in (select r_id from informatics_expert_rules)
		and m_id in (select m_id from informatics_expert_membership_functions)
		and (v_id is null or v_id in (select v_id from informatics_expert_variables))
with cascaded check option;

create view informatics_expert_rule_antecedents as
	select * from rule_antecedents ra
	where r_id in (select r_id from informatics_expert_rules)
		and a_id in (select a_id from informatics_expert_antecedents)
with cascaded check option;

------------------------------------------------------


grant all on informatics_expert_variables to informatics;
grant all on informatics_expert_antecedents to informatics;
grant all on informatics_expert_rule_antecedents to informatics;
grant all on informatics_expert_consequents to informatics;
grant all on informatics_expert_membership_functions to informatics;
grant all on informatics_expert_rules to informatics;
grant all on informatics_expert_systems to informatics;



create user physic inherit
login
password '892';
grant physics to physic;

create user chemist inherit
login
password '9222';
grant chemistry to chemist;

create user programmer inherit
login
password 'hfdh';
grant informatics to programmer;




