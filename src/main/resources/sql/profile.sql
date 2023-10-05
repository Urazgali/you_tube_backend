
-- Insert Query
INSERT INTO public.profile (id, visible, created_date, name, surnema, email, password, photo_id, status, role,
                            prtId)
VALUES (1, true, '2023-08-14 16:41:33.000000', 'Ali', 'Aliyev', 'ali123@gmail.com', '12345678', 1, 'ACTIVE', 'ROLE_USER', '2');

-- Update Detail Query
update profile as p set p.name = 'Vali', p.surname = 'Valiyev' where p.id = 1;

-- Get Detail Query
select * from profile as p where p.id = 1;

select * from profile;