import React from "react";
import { useState } from "react";
import PetItem from "../components/PetItem";
import "../css/ProfilePet.css";
import SiderbarPet from "../components/SiderbarPet";
const ProfilePet = () => {
  const [pets, setPets] = useState([
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
      imageUrl: "https://example.com/buddy.jpg",
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
      imageUrl: "https://example.com/miu.jpg",
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
      imageUrl: "https://example.com/milo.jpg",
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
      imageUrl: "https://example.com/bong.jpg",
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
      imageUrl: "https://example.com/susu.jpg",
    },
  ]);
  const [showSidebarPet, setShowSidebarPet] = useState(false);
  const [activePetItemId, setActivePetItemId] = useState(null);
  // Xử lý mở form khi nhấn btn-add
  const handleShowSidebarPet = () => {
    setActivePetItemId(null);
    setShowSidebarPet(true);
  };
  // thoát form
  const handleCancelPetForm = () => {
    setShowSidebarPet(false);
  };
  //xem - sửa
  const handleShowSidebarPetID = (petId) => {
    setShowSidebarPet(true);
    setActivePetItemId(petId);
  };
  const activePetItem = pets.find((pet) => pet.id === activePetItemId);
  // Xử lý lưu từ form
  const handleSavePet = (data) => {
    if (activePetItemId) {
      // cập nhật pet
      setPets(
        pets.map((pet) =>
          pet.id === activePetItemId ? { ...pet, ...data } : pet
        )
      );
    } else {
      // thêm mới pet
      const newPet = {
        id: pets.length + 1,
        ...data,
      };
      setPets([...pets, newPet]);
    }
    setShowSidebarPet(false);
  };
  // xử lý xóa
  const handleDeletePet = (petId) => {
    // lọc ra danh sách mới không có petId
    const newPets = pets.filter((pet) => pet.id !== petId);
    setPets(newPets);

    // nếu đang mở sidebar của pet bị xóa, đóng sidebar
    if (activePetItemId === petId) {
      setShowSidebarPet(false);
      setActivePetItemId(null);
    }
  };
  return (
    <>
      <div className="main-item">
        {pets.map((pet) => (
          <PetItem
            key={pet.id}
            {...pet}
            handleShowSidebarPetID={handleShowSidebarPetID}
            handleDeletePet={handleDeletePet}
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
      {showSidebarPet && (
        <SiderbarPet
          key={activePetItemId}
          petItem={activePetItem}
          handleCancelPetForm={handleCancelPetForm}
          handleSavePet={handleSavePet}
        />
      )}
    </>
  );
};

export default ProfilePet;
