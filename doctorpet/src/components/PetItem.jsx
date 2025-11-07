import "remixicon/fonts/remixicon.css";
import "../css/components/PetItem.css";
import { useState } from "react";

const PetItem = (props) => {
  const [showMenu, setShowMenu] = useState(false);
  const handleShowMenu = () => {
    setShowMenu(!showMenu);
  };
  const handleViewOrEdit = () => {
    props.handleShowSidebarPetID(props.id);
    setShowMenu(false);
  };
  return (
    <div className="pet-item">
      <div className="pet-menu" onClick={handleShowMenu}>
        <i className="ri-more-2-fill"></i>
      </div>
      {showMenu && (
        <div className="pet-option">
          <button className="pet-delete">Xóa</button>
          <button className="pet-update" onClick={handleViewOrEdit}>Sửa</button>
          <button className="pet-read" onClick={handleViewOrEdit}>Xem</button>
        </div>
      )}

      <img className="pet-avatar" src={props.image} alt="pet" />

      <div className="pet-info">
        <div className="pet-field">{props.name}</div>
        <div className="pet-field">{props.dateOfBirth}</div>
        <div className="pet-field">{props.weight}</div>
        <div className="pet-field">{props.allergies}</div>
      </div>
    </div>
  );
};

export default PetItem;
