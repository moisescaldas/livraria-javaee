DELETE FROM Checkouts;

ALTER TABLE Checkouts
ADD CHECKOUT_UUID VARCHAR(36) NOT NULL;