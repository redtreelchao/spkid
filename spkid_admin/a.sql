DELIMITER $$

USE `spkid`$$

DROP PROCEDURE IF EXISTS `provider_onsale_product_num`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `provider_onsale_product_num`()
BEGIN

UPDATE ty_product_provider pp,
  (SELECT
     pii.provider_id,
     COUNT(DISTINCT pii.product_id) AS num
   FROM ty_product_info pii
     INNER JOIN ty_product_sub ps
       ON pii.product_id = ps.product_id
   WHERE pii.is_audit = 1
       AND ps.is_on_sale = 1
       AND (ps.consign_num > 0
             OR ps.consign_num =  - 2
             OR ps.gl_num > ps.wait_num)
   GROUP BY pii.provider_id) a
SET pp.product_num = a.num
WHERE pp.provider_id = a.provider_id;

END$$

DELIMITER ;