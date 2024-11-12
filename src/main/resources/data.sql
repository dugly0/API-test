INSERT INTO company (id, name) VALUES 
(1, 'Acme Corporation'),
(2, 'Globex Industries');

INSERT INTO users (id, name, role, company_id) VALUES 
(1, 'John Doe', 'STANDARD', 1),
(2, 'Jane Smith', 'COMPANY_ADMIN', 1),
(3, 'Alice Johnson', 'STANDARD', 2),
(4, 'Bob Williams', 'COMPANY_ADMIN', 2),
(5, 'Admin Master', 'SUPER_USER', null);

INSERT INTO task (id, description, user_id) VALUES 
(1, 'Finish the report', 1),
(2, 'Schedule a meeting', 1),
(3, 'Review code', 2),
(4, 'Approve budget', 2),
(5, 'Create presentation', 5),
(6, 'Send email to client', 3),
(7, 'Prepare for conference', 4);