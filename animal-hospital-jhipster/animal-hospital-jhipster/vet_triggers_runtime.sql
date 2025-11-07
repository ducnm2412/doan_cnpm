USE animalhospital;
DROP TRIGGER IF EXISTS trg_vets_insert_check_role;
DROP TRIGGER IF EXISTS trg_vets_update_check_role;
DELIMITER $$
CREATE TRIGGER trg_vets_insert_check_role
BEFORE INSERT ON vet
FOR EACH ROW
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM jhi_user_authority ua
    JOIN jhi_authority a ON a.name = ua.authority_name
    WHERE ua.user_id = NEW.user_id AND a.name = 'ROLE_DOCTOR'
  ) THEN
    SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'User must have ROLE_DOCTOR authority to be assigned as vet';
  END IF;
END$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER trg_vets_update_check_role
BEFORE UPDATE ON vet
FOR EACH ROW
BEGIN
  IF NEW.user_id <> OLD.user_id THEN
    IF NOT EXISTS (
      SELECT 1
      FROM jhi_user_authority ua
      JOIN jhi_authority a ON a.name = ua.authority_name
      WHERE ua.user_id = NEW.user_id AND a.name = 'ROLE_DOCTOR'
    ) THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User must have ROLE_DOCTOR authority to be assigned as vet';
    END IF;
  END IF;
END$$
DELIMITER ;








