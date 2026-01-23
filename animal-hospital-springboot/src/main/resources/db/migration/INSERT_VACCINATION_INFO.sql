-- =====================================================
-- Script INSERT thông tin về tiêm phòng cho chó và mèo
-- =====================================================

USE animalhospital;

-- =====================================================
-- 1. TIÊM PHÒNG CHO CHÓ
-- =====================================================

INSERT INTO disease_dog (title, keywords, content, severity_level, is_active, created_date) VALUES

-- 1. Lịch tiêm phòng cho chó
('Lịch tiêm phòng cho chó - Vaccine cần thiết',
 'tiêm phòng,vaccine,chủng ngừa,lịch tiêm phòng,tiêm chủng,phòng bệnh cho chó',
 'Lịch tiêm phòng cho chó:

**Chó con (dưới 1 tuổi):**
- 6-8 tuần tuổi: Mũi 1 (DHPPi - 5 bệnh: Distemper, Hepatitis, Parvovirus, Parainfluenza)
- 10-12 tuần tuổi: Mũi 2 (DHPPi + Leptospirosis)
- 14-16 tuần tuổi: Mũi 3 (DHPPi + Leptospirosis + Rabies - Dại)
- 16-20 tuần tuổi: Mũi 4 (DHPPi - nếu cần)

**Chó trưởng thành (trên 1 tuổi):**
- Tiêm nhắc lại hàng năm: DHPPi + Leptospirosis + Rabies
- Tiêm dại: Bắt buộc theo quy định pháp luật

**Các loại vaccine:**
- DHPPi: Phòng 5 bệnh nguy hiểm
- Rabies (Dại): Bắt buộc, phòng bệnh dại
- Leptospirosis: Phòng bệnh do vi khuẩn Leptospira
- Bordetella: Phòng ho cũi chó (nếu cần)

**Lưu ý:**
- Chó phải khỏe mạnh khi tiêm phòng
- Theo dõi phản ứng sau tiêm (sốt nhẹ, mệt mỏi là bình thường)
- Không tắm chó 3-5 ngày sau tiêm
- Giữ ấm và tránh stress sau tiêm',
 'GENERAL',
 TRUE,
 NOW()),

-- 2. Các bệnh phòng ngừa bằng vaccine
('Các bệnh phòng ngừa bằng vaccine cho chó',
 'vaccine,phòng bệnh,distemper,parvovirus,hepatitis,rabies,dại,ho cũi chó',
 'Các bệnh quan trọng được phòng ngừa bằng vaccine:

**1. Distemper (Bệnh Care):**
- Bệnh do virus, rất nguy hiểm, tỷ lệ tử vong cao
- Triệu chứng: Sốt, nôn, tiêu chảy, co giật, viêm phổi
- Phòng ngừa: Vaccine DHPPi

**2. Parvovirus:**
- Bệnh đường ruột nguy hiểm, đặc biệt ở chó con
- Triệu chứng: Nôn, tiêu chảy ra máu, mất nước nặng
- Phòng ngừa: Vaccine DHPPi

**3. Hepatitis (Viêm gan):**
- Bệnh do virus gây tổn thương gan
- Triệu chứng: Vàng da, nôn, tiêu chảy, suy gan
- Phòng ngừa: Vaccine DHPPi

**4. Rabies (Bệnh Dại):**
- Bệnh nguy hiểm, lây sang người, tử vong 100%
- Bắt buộc tiêm phòng theo pháp luật
- Phòng ngừa: Vaccine Rabies

**5. Leptospirosis:**
- Bệnh do vi khuẩn, lây qua nước, đất, nước tiểu động vật
- Có thể lây sang người
- Phòng ngừa: Vaccine Leptospirosis

**6. Bordetella (Ho cũi chó):**
- Bệnh đường hô hấp, dễ lây lan
- Triệu chứng: Ho khan, nôn, khó thở
- Phòng ngừa: Vaccine Bordetella (nếu cần)',
 'GENERAL',
 TRUE,
 NOW()),

-- 3. Phản ứng sau tiêm phòng
('Phản ứng sau tiêm phòng ở chó - Cách xử lý',
 'phản ứng sau tiêm,sốt sau tiêm,mệt mỏi sau tiêm,tác dụng phụ vaccine,đau sau tiêm',
 'Phản ứng sau tiêm phòng ở chó:

**Phản ứng bình thường (24-48 giờ):**
- Sốt nhẹ (38.5-39.5°C)
- Mệt mỏi, ngủ nhiều
- Chán ăn tạm thời
- Sưng nhẹ tại vị trí tiêm
- Đau nhẹ khi chạm vào

**Cách xử lý:**
- Cho chó nghỉ ngơi, giữ ấm
- Đảm bảo có nước sạch
- Không tắm 3-5 ngày sau tiêm
- Tránh vận động mạnh
- Theo dõi nhiệt độ

**Phản ứng nghiêm trọng (cần đến bác sĩ ngay):**
- Sốt cao trên 40°C
- Nôn liên tục
- Tiêu chảy nặng
- Co giật, run rẩy
- Khó thở, sưng mặt/môi
- Phản ứng dị ứng (nổi mề đay, ngứa)
- Bỏ ăn hoàn toàn quá 48 giờ

**Lưu ý:**
- Giữ sổ tiêm phòng để theo dõi lịch tiêm
- Báo bác sĩ nếu có phản ứng bất thường
- Không tự ý cho uống thuốc hạ sốt của người',
 'MODERATE',
 TRUE,
 NOW());

-- =====================================================
-- 2. TIÊM PHÒNG CHO MÈO
-- =====================================================

INSERT INTO disease_cat (title, keywords, content, severity_level, is_active, created_date) VALUES

-- 1. Lịch tiêm phòng cho mèo
('Lịch tiêm phòng cho mèo - Vaccine cần thiết',
 'tiêm phòng,vaccine,chủng ngừa,lịch tiêm phòng,tiêm chủng,phòng bệnh cho mèo',
 'Lịch tiêm phòng cho mèo:

**Mèo con (dưới 1 tuổi):**
- 6-8 tuần tuổi: Mũi 1 (FVRCP - 3 bệnh: Feline Viral Rhinotracheitis, Calicivirus, Panleukopenia)
- 10-12 tuần tuổi: Mũi 2 (FVRCP)
- 14-16 tuần tuổi: Mũi 3 (FVRCP + Rabies - Dại)
- 16-20 tuần tuổi: Mũi 4 (FVRCP - nếu cần)

**Mèo trưởng thành (trên 1 tuổi):**
- Tiêm nhắc lại hàng năm: FVRCP + Rabies
- Mèo nuôi trong nhà: Có thể tiêm 3 năm/lần (theo khuyến nghị bác sĩ)
- Mèo nuôi ngoài trời: Nên tiêm hàng năm

**Các loại vaccine:**
- FVRCP: Phòng 3 bệnh nguy hiểm (Feline Viral Rhinotracheitis, Calicivirus, Panleukopenia)
- Rabies (Dại): Bắt buộc, phòng bệnh dại
- FeLV (Feline Leukemia): Khuyến nghị cho mèo nuôi ngoài trời hoặc tiếp xúc với mèo khác
- FIV (Feline Immunodeficiency): Không có vaccine, chỉ phòng ngừa bằng cách tránh tiếp xúc

**Lưu ý:**
- Mèo phải khỏe mạnh khi tiêm phòng
- Theo dõi phản ứng sau tiêm (sốt nhẹ, mệt mỏi là bình thường)
- Không tắm mèo 3-5 ngày sau tiêm
- Giữ ấm và tránh stress sau tiêm',
 'GENERAL',
 TRUE,
 NOW()),

-- 2. Các bệnh phòng ngừa bằng vaccine
('Các bệnh phòng ngừa bằng vaccine cho mèo',
 'vaccine,phòng bệnh,fvr,calicivirus,panleukopenia,rabies,dại,feLV,feline leukemia',
 'Các bệnh quan trọng được phòng ngừa bằng vaccine:

**1. Feline Viral Rhinotracheitis (FVR - Cảm cúm mèo):**
- Bệnh do virus Herpes, rất phổ biến
- Triệu chứng: Sốt, chảy nước mắt, chảy nước mũi, ho, bỏ ăn
- Phòng ngừa: Vaccine FVRCP

**2. Calicivirus:**
- Bệnh đường hô hấp và miệng
- Triệu chứng: Loét miệng, chảy nước mũi, ho, viêm phổi
- Phòng ngừa: Vaccine FVRCP

**3. Panleukopenia (Bệnh giảm bạch cầu):**
- Bệnh nguy hiểm, tỷ lệ tử vong cao, đặc biệt ở mèo con
- Triệu chứng: Sốt cao, nôn, tiêu chảy ra máu, suy giảm miễn dịch
- Phòng ngừa: Vaccine FVRCP

**4. Rabies (Bệnh Dại):**
- Bệnh nguy hiểm, lây sang người, tử vong 100%
- Bắt buộc tiêm phòng theo pháp luật
- Phòng ngừa: Vaccine Rabies

**5. FeLV (Feline Leukemia Virus):**
- Bệnh suy giảm miễn dịch, có thể gây ung thư
- Lây qua tiếp xúc gần, nước bọt, máu
- Phòng ngừa: Vaccine FeLV (khuyến nghị cho mèo nuôi ngoài trời)

**6. FIV (Feline Immunodeficiency Virus):**
- Tương tự HIV ở người, suy giảm miễn dịch
- Lây qua vết cắn, giao phối
- Không có vaccine, chỉ phòng ngừa bằng cách tránh tiếp xúc',
 'GENERAL',
 TRUE,
 NOW()),

-- 3. Phản ứng sau tiêm phòng
('Phản ứng sau tiêm phòng ở mèo - Cách xử lý',
 'phản ứng sau tiêm,sốt sau tiêm,mệt mỏi sau tiêm,tác dụng phụ vaccine,đau sau tiêm',
 'Phản ứng sau tiêm phòng ở mèo:

**Phản ứng bình thường (24-48 giờ):**
- Sốt nhẹ (38.5-39.5°C)
- Mệt mỏi, ngủ nhiều
- Chán ăn tạm thời
- Sưng nhẹ tại vị trí tiêm
- Đau nhẹ khi chạm vào

**Cách xử lý:**
- Cho mèo nghỉ ngơi, giữ ấm
- Đảm bảo có nước sạch và thức ăn
- Không tắm 3-5 ngày sau tiêm
- Tránh làm mèo căng thẳng
- Theo dõi nhiệt độ

**Phản ứng nghiêm trọng (cần đến bác sĩ ngay):**
- Sốt cao trên 40°C
- Nôn liên tục
- Tiêu chảy nặng
- Co giật, run rẩy
- Khó thở, sưng mặt/môi
- Phản ứng dị ứng (nổi mề đay, ngứa)
- Bỏ ăn hoàn toàn quá 48 giờ
- U bướu tại vị trí tiêm (hiếm gặp, nhưng cần theo dõi)

**Lưu ý:**
- Giữ sổ tiêm phòng để theo dõi lịch tiêm
- Báo bác sĩ nếu có phản ứng bất thường
- Không tự ý cho uống thuốc hạ sốt của người
- Mèo có thể ẩn nấp nhiều hơn sau tiêm (bình thường)',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT 'disease_dog' as table_name, COUNT(*) as count FROM disease_dog WHERE keywords LIKE '%tiêm phòng%' OR keywords LIKE '%vaccine%'
UNION ALL
SELECT 'disease_cat' as table_name, COUNT(*) as count FROM disease_cat WHERE keywords LIKE '%tiêm phòng%' OR keywords LIKE '%vaccine%';
