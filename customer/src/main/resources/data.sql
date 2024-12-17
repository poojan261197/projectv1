-- Insert default data into the customers table
INSERT INTO customers (id, first_name, middle_name, last_name, email_address, phone_number, version)
VALUES
  ('7c9c87d9-01de-4671-8c89-b1c1a5b7c2fc', 'John', 'A', 'Doe', 'john.doe@example.com', '123-456-7890',1),
  ('2a4d7a10-45cb-4c65-8d32-8373f5b56f69', 'Jane', 'B', 'Smith', 'jane.smith@example.com', '987-654-3210',1),
  ('5f6c0b22-3d7f-4924-8773-b42c6c6b9352', 'Alice', NULL, 'Johnson', 'alice.johnson@example.com', '555-555-5555',1),
  ('a2c29dff-83bc-4b7b-b6d0-59b2b0577b79', 'Bob', NULL, 'Williams', 'bob.williams@example.com', '333-333-3333',1);
