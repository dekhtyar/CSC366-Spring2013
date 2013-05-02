--  Slightly Blue Team
-- 
--  Alejandro Cervantes (acerva01)
--  Corey Farwell
--  Kyle Sletten
--  Lance Tyler
--  Ray Tam (rmtam)
-- 
--  CPE366 - Spring 2013


-- Define Store Locations -----------------------------------------------------

INSERT INTO Location
   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);


-- Define Store Bins ----------------------------------------------------------

INSERT INTO Bins
   VALUES (?, ?, ?);


-- Bulk Inventory Update ------------------------------------------------------

-- IF item doesn't exist
   INSERT INTO FulfillerItem
      VALUES (?, ?, ?);
-- END

-- IF on-hand doesn't exist
   INSERT INTO OnHand
      VALUES (?, ?, ?, ?, 0, 0);
-- END

-- IF stock doesn't exist
   -- IF safety is overridden
      INSERT INTO Stock
         VALUES (?, ?, ?, 0, ?, ?);
   -- ELSE
      INSERT INTO Stock (FulfillerID, ExternalID, SKU, Quantity, Safety, LTD)
         SELECT FulfillerID, ExternalID, ?, 0, Safety, ?
         FROM Location
            WHERE FulfillerID = ?
               AND ExternalID = ?;
   -- END
-- END

UPDATE Stock
   SET Quantity = Quantity + ? - (
      SELECT Quantity
         FROM OnHand
         WHERE FullfillerID = ?
            AND ExternalID = ?
            AND SKU = ?)
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;

UPDATE OnHand
   SET Quantity = ?
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;


-- Trickle Inventory Update ---------------------------------------------------

UPDATE OnHand
   SET Quantity = Quantity + ?,
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;

UPDATE Stock
   SET Quantity = Quantity + ?,
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;


-- Get Inventory -------------------------------------------------------------

SELECT *
   FROM OnHand o, Bin b, Location l, Seller se, FulfillerItem f, Stock st
   WHERE o.FulfillerID = b.FulfillerID
      AND o.ExternalID = b.ExternalID
      AND o.Name = b.Name
      AND o.FulfillerID = l.FulfillerID
      AND o.ExternalID = l.ExternalID
      AND se.FulfillerID = l.FulfillerID
      AND se.ExternalID = l.ExternalID
      AND o.FulfillerID = f.FulfillerID
      AND st.FulfillerID = f.FulfillerID
      AND st.ExternalID = f.ExternalID
      AND st.SKU = f.SKU

      AND l.StoreType = ?
      AND se.MfcID = ?
      AND se.CatalogID = ?
      AND f.SKU = ?
      AND f.UPC = ?

      -- IF safety enforced
         AND st.Quantity - ? > st.Safety
      -- END

      -- IF negative inventory
         AND st.Quantity - ? > 0
      -- END 

      ORDER BY
         -- IF order by LTD
         st.LTD
         -- ELSE
         st.Quantity
         -- END 
      DESC;


-- Allocate Inventory --------------------------------------------------------

UPDATE OnHand
   SET Allocated = Allocated + ?
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;

INSERT INTO OnHandOrder
   VALUES (?, ?, ?, ?, ?, ?, ?);



-- De-allocate Inventory ------------------------------------------------------

-- Fulfilled
UPDATE OnHand
   SET Allocated = Allocated - ?,
      Quantity = Quantity - ?
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;
 

-- Cancelled 
UPDATE OnHand
   SET Allocated = Allocated - ?
   WHERE FullfillerID = ?
      AND ExternalID = ?
      AND SKU = ?;
