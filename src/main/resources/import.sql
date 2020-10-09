INSERT INTO customer (id, name, surname) VALUES ((SELECT next_val FROM customerId_seq), 'John','Doe');
UPDATE customerid_seq SET next_val = 2;
INSERT INTO customer (id, name, surname) VALUES ((SELECT next_val FROM customerId_seq), 'Fred','Smith');
UPDATE customerid_seq SET next_val = 3;
