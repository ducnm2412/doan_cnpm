import React from "react";
import { useState, useEffect } from "react";
import "../css/SidebarPet.css";
const SiderbarPet = (props) => {
  const pet = props.petItem;
  const [formData, setFormData] = useState({
    name: "",
    species: "",
    breed: "",
    sex: "",
    dateOfBirth: "",
    weight: "",
    allergies: "",
    notes: "",
    imageUrl: "",
  });

  useEffect(() => {
    if (pet) {
      setFormData({
        name: pet.name || "",
        species: pet.species || "",
        breed: pet.breed || "",
        sex: pet.sex || "",
        dateOfBirth: pet.dateOfBirth || "",
        weight: pet.weight || "",
        allergies: pet.allergies || "",
        notes: pet.notes || "",
        imageUrl: pet.imageUrl || "",
      });
    }
  }, [pet]);
  // xử lý gõ input
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  const handleSubmit = (e) => {
  e.preventDefault(); // ngăn browser gửi form
  props.handleSavePet(formData); // gửi dữ liệu lên parent hoặc API
};

  const handleCancel = (e) => {
    e.preventDefault();
    props.handleCancelPetForm();
  };
  return (
    <div className={`pet-sidebar show`}>
      <form className="pet-form" onSubmit={handleSubmit}>
        <h3>Thú Cưng Của Bạn</h3>

        <label className="pet-form-label">Tên thú cưng*</label>
        <input className="pet-form-input" type="text" name="name" value={formData.name} onChange={handleChange} required />

        <label className="pet-form-label">Loài</label>
        <input className="pet-form-input" type="text" name="species" value={formData.species} onChange={handleChange} />

        <label className="pet-form-label">Giống loài*</label>
        <input className="pet-form-input" type="text" name="breed" value={formData.breed} onChange={handleChange} required />

        <label className="pet-form-label">Giới tính*</label>
        <select className="pet-form-select" name="sex" value={formData.sex} onChange={handleChange}>
          <option value="">--Chọn giới tính--</option>
          <option value="Đực">Đực</option>
          <option value="Cái">Cái</option>
        </select>

        <label className="pet-form-label">Ngày sinh*</label>
        <input className="pet-form-input" type="date" name="dateOfBirth" value={formData.dateOfBirth} onChange={handleChange} />

        <label className="pet-form-label">Cân nặng (kg)</label>
        <input className="pet-form-input" type="number" name="weight" value={formData.weight} onChange={handleChange} />

        <label className="pet-form-label">Dị ứng</label>
        <input className="pet-form-input" type="text" name="allergies" value={formData.allergies} onChange={handleChange} />

        <label className="pet-form-label">Ghi chú</label>
        <textarea className="pet-form-textarea" name="notes" value={formData.notes} onChange={handleChange}></textarea>

        <label className="pet-form-label">Ảnh URL</label>
        <input className="pet-form-input" type="text" name="imageUrl" value={formData.imageUrl} onChange={handleChange} />

        <div className="sb-footer">
          <button className="save-btn" type="submit">Lưu</button>
          <button className="cancel-btn" onClick={handleCancel}>Hủy</button>
        </div>
      </form>
    </div>
  );

};

export default SiderbarPet;
