DROP TABLE IF EXISTS observers;
DROP TABLE IF EXISTS companies;

CREATE TABLE companies (
    id SERIAL PRIMARY KEY, 
    name VARCHAR(255) NOT NULL UNIQUE, 
    address TEXT, 
    date_registered DATE NOT NULL DEFAULT CURRENT_DATE, 
    contact_person VARCHAR(100), 
    telephone_number VARCHAR(20), 
    email_address VARCHAR(255) UNIQUE 
);

CREATE TABLE observers (
    id SERIAL PRIMARY KEY, 
    name VARCHAR(255) NOT NULL, 
    address TEXT, 
    date_created DATE NOT NULL DEFAULT CURRENT_DATE, 
    telephone_number VARCHAR(20), 
    email_address VARCHAR(255) UNIQUE,
    company_id INT NOT NULL,

    CONSTRAINT fk_company FOREIGN KEY(company_id) REFERENCES companies(id) ON DELETE CASCADE
);

