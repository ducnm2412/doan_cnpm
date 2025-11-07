import React, { useState } from "react";
import "../css/UserLayout.css";
import Header from "../components/Header";
import Footer from "../components/Footer";
import PetItem from "../components/PetItem";
import "remixicon/fonts/remixicon.css";
import SiderbarPet from "../components/SiderbarPet";

const UserLayout = () => {
  const pets = [
  {
    id: 1,
    name: "Buddy",
    species: "Chó",
    breed: "Golden Retriever",
    sex: "Đực",
    dateOfBirth: "2020-05-15",
    weight: 25.5,
    allergies: "Không",
    notes: "Thú cưng rất thân thiện",
    imageUrl: "https://example.com/buddy.jpg"
  },
  {
    id: 2,
    name: "MiuMiu",
    species: "Mèo",
    breed: "Mèo Anh Lông Ngắn",
    sex: "Cái",
    dateOfBirth: "2022-03-10",
    weight: 4.2,
    allergies: "Bụi phấn",
    notes: "Rất thích ngủ và nằm trên ghế sofa",
    imageUrl: "https://example.com/miu.jpg"
  },
  {
    id: 3,
    name: "Milo",
    species: "Chó",
    breed: "Corgi",
    sex: "Đực",
    dateOfBirth: "2021-09-05",
    weight: 12.3,
    allergies: "Không",
    notes: "Thông minh và thích chạy nhảy",
    imageUrl: "https://example.com/milo.jpg"
  },
  {
    id: 4,
    name: "Bông",
    species: "Mèo",
    breed: "Ba Tư",
    sex: "Cái",
    dateOfBirth: "2019-12-22",
    weight: 3.8,
    allergies: "Hải sản",
    notes: "Lông dài, cần chải thường xuyên",
    imageUrl: "https://example.com/bong.jpg"
  },
  {
    id: 5,
    name: "Susu",
    species: "Chó",
    breed: "Poodle",
    sex: "Cái",
    dateOfBirth: "2023-01-14",
    weight: 6.0,
    allergies: "Sữa bò",
    notes: "Dễ thương, hay sủa khi gặp người lạ",
    imageUrl: "https://example.com/susu.jpg"
  }
];
  {
    /* ADD-PET-ITEM*/
  }
  const [showSidebarPet, setShowSidebarPet] = useState(false);
  const handleShowSidebarPet = () => {
    setShowSidebarPet(true);
  };
// Cancel-Pet-Form
const handelCancelPetForm = () => {
  setShowSidebarPet(false)
}
//xem - sửa
const [activePetItemId, setActivePetItemId] = useState();
const handleShowSidebarPetID = (petId) => {
  setShowSidebarPet(true)
  setActivePetItemId(petId)
}
const activePetItem = pets.find((pet) => pet.id === activePetItemId); 
  return (
    <>
      <Header />
      <div className="dashboard-container">
        {/* Sidebar */}
        <div className="sidebar">
          <div className="profile-section">
            <img className="avatar" src="../public/assets/meme.jpg"></img>
            <input
              type="text"
              placeholder="Tên người dùng"
              className="info-input"
            />
            <input type="text" placeholder="Email" className="info-input" />
            <input
              type="text"
              placeholder="Số điện thoại"
              className="info-input"
            />
          </div>

          <div className="menu-section">
            <button className="menu-btn">Hồ sơ thú cưng</button>
            <button className="menu-btn">Đặt lịch khám</button>
            <button className="menu-btn">Lịch đã đặt</button>
            <button className="menu-btn">Đặt câu hỏi</button>
          </div>
        </div>

        {/* Main content */}
        <div className="main-content">
          <div className="main-item">
            {pets.map((pet) => (
              <PetItem
                id={pet.id}
                name={pet.name}
                species={pet.species}
                breed={pet.breed}
                sex={pet.sex}
                dateOfBirth={pet.dateOfBirth}
                weight={pet.weight}
                allergies={pet.allergies}
                notes={pet.notes}
                image={pet.image}
                handleShowSidebarPetID = {handleShowSidebarPetID}
              />
            ))}
            <div>
              <div className="add-item" onClick={handleShowSidebarPet}>
                <div className="add-item-icon">
                  <i class="ri-add-line"></i>
                </div>
              </div>
            </div>
          </div>
          {showSidebarPet && <SiderbarPet 
          key={activePetItemId}
          petItem={activePetItem}
          handelCancelPetForm={handelCancelPetForm}
          handleShowSidebarPetID={handleShowSidebarPetID}
          />}
        </div>
      </div>
    </>
  );
};

export default UserLayout;
