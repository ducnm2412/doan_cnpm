-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_bird
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_bird (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. HÔ HẤP (RẤT PHỔ BIẾN Ở CHIM)
-- =====================================================

-- 1. Ho / Khó thở / Thở gấp
('Ho, khó thở, thở gấp ở chim',
 'ho,ho khan,khó thở,thở gấp,thở nhanh,thở khó,thở há miệng,thở nặng nhọc,thở khò khè',
 'Ho, khó thở, thở gấp ở chim là dấu hiệu nghiêm trọng và cần được điều trị ngay. Nghi ngờ: Nhiễm khuẩn đường hô hấp (Chlamydiosis, Mycoplasmosis), Viêm phổi, Viêm phế quản, Nhiễm nấm đường hô hấp (Aspergillosis), Dị vật đường thở, Bệnh tim, Stress nhiệt. Follow-up: Có chảy nước mũi không? Có chảy nước mắt không? Có sốt không? Có tím lưỡi / lợi không? Có đuôi nhấp nháy không? Khó thở ở chim là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 2. Chảy nước mũi / Hắt hơi / Nghẹt mũi
('Chảy nước mũi, hắt hơi, nghẹt mũi ở chim',
 'chảy nước mũi,hắt hơi,nghẹt mũi,nước mũi,chảy mũi,sổ mũi,thở khò khè mũi',
 'Chảy nước mũi, hắt hơi, nghẹt mũi ở chim thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Nhiễm khuẩn đường hô hấp (Chlamydiosis, Mycoplasmosis), Viêm mũi, Viêm xoang, Nhiễm nấm đường hô hấp (Aspergillosis), Dị ứng, Kích ứng từ môi trường. Follow-up: Có ho không? Có khó thở không? Có chảy nước mắt không? Có sốt không? Cần đưa chim đến bác sĩ thú y để kiểm tra và điều trị bằng kháng sinh nếu cần.',
 'MODERATE',
 TRUE,
 NOW()),

-- 3. Đuôi nhấp nháy / Đuôi nhấp nháy khi thở
('Đuôi nhấp nháy khi thở ở chim',
 'đuôi nhấp nháy,đuôi nhấp nháy khi thở,đuôi nhấp nháy liên tục,đuôi nhấp nháy mạnh',
 'Đuôi nhấp nháy khi thở là dấu hiệu đặc trưng của khó thở ở chim. Nghi ngờ: Nhiễm khuẩn đường hô hấp (Chlamydiosis, Mycoplasmosis), Viêm phổi, Viêm phế quản, Nhiễm nấm đường hô hấp (Aspergillosis), Bệnh tim, Dị vật đường thở. Follow-up: Có ho không? Có khó thở không? Có chảy nước mũi không? Có sốt không? Đuôi nhấp nháy là dấu hiệu nghiêm trọng, cần đưa chim đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. TIÊU HÓA (RẤT HAY GẶP Ở CHIM)
-- =====================================================

-- 4. Tiêu chảy / Phân lỏng / Phân bất thường
('Tiêu chảy, phân lỏng, phân bất thường ở chim',
 'tiêu chảy,phân lỏng,đi phân lỏng,phân không thành khuôn,phân bất thường,phân màu lạ',
 'Tiêu chảy hoặc phân bất thường ở chim là dấu hiệu nghiêm trọng. Nghi ngờ: Nhiễm khuẩn đường ruột (E.coli, Salmonella), Viêm đường ruột, Thay đổi thức ăn đột ngột, Ngộ độc thức ăn, Ký sinh trùng đường ruột, Bệnh PDD (Proventricular Dilatation Disease), Bệnh gan. Follow-up: Có bỏ ăn không? Có nôn không? Có phân có máu không? Có mệt mỏi không? Chim tiêu chảy cần được đưa đến bác sĩ thú y ngay để điều trị và bù nước.',
 'SEVERE',
 TRUE,
 NOW()),

-- 5. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở chim',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở chim là dấu hiệu nghiêm trọng vì chim có tốc độ trao đổi chất rất cao. Nghi ngờ: Nhiễm trùng đường hô hấp, Bệnh răng miệng (mỏ), Stress, Nhiễm khuẩn, Bệnh đường tiêu hóa, Ngộ độc, Bệnh PDD. Follow-up: Có ho / khó thở không? Có chảy nước mũi không? Có nôn không? Có sụt cân không? Nếu chim bỏ ăn quá 12-24 giờ, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 6. Nôn / Ói / Nôn ra thức ăn
('Nôn, ói, nôn ra thức ăn ở chim',
 'nôn,ói,nôn ra thức ăn,ói ra bọt,buồn nôn,nôn mửa',
 'Nôn ở chim có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Bệnh PDD (Proventricular Dilatation Disease), Nhiễm khuẩn đường ruột, Ngộ độc, Tắc nghẽn đường ruột, Nuốt dị vật, Bệnh gan, Bệnh thận. Follow-up: Có bỏ ăn không? Có tiêu chảy không? Có mệt mỏi không? Có sụt cân không? Nôn ở chim là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. DA LÔNG (PHỔ BIẾN Ở CHIM)
-- =====================================================

-- 7. Rụng lông / Tự nhổ lông
('Rụng lông, tự nhổ lông ở chim',
 'rụng lông,tự nhổ lông,nhổ lông nhiều,lông rụng,hói,rụng lông theo mảng',
 'Rụng lông hoặc tự nhổ lông ở chim có thể do nhiều nguyên nhân. Nghi ngờ: Stress, Buồn chán, Thiếu dinh dưỡng, Nhiễm ký sinh trùng ngoài da, Nấm da, Rối loạn nội tiết, Bệnh da dị ứng, Bệnh gan. Follow-up: Có ngứa / gãi nhiều không? Có da đỏ / viêm da không? Có thay đổi môi trường sống không? Có thiếu đồ chơi / kích thích không? Cần đưa chim đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 8. Ngứa / Gãi nhiều
('Ngứa, gãi nhiều ở chim',
 'ngứa,gãi nhiều,gãi liên tục,ngứa ngáy,ngứa da,gãi mỏ',
 'Ngứa và gãi nhiều ở chim có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm ký sinh trùng ngoài da (ve, rận), Nấm da, Viêm da dị ứng, Kích ứng da, Thiếu dinh dưỡng. Follow-up: Có rụng lông không? Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Cần đưa chim đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Da đỏ / Viêm da / Mụn
('Da đỏ, viêm da, mụn ở chim',
 'da đỏ,viêm da,da bị đỏ,viêm da mủ,da sưng đỏ,mụn,mụn mủ,mụn trên da',
 'Da đỏ, viêm da, mụn ở chim có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn da, Nấm da, Viêm da dị ứng, Ký sinh trùng ngoài da, Tự nhổ lông gây tổn thương da, Áp xe da. Follow-up: Có ngứa / gãi nhiều không? Có chảy mủ không? Có sưng không? Có mùi hôi không? Cần đưa chim đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. MẮT (PHỔ BIẾN Ở CHIM)
-- =====================================================

-- 10. Mắt đỏ / Chảy ghèn / Chảy nước mắt
('Mắt đỏ, chảy ghèn, chảy nước mắt ở chim',
 'mắt đỏ,chảy ghèn,chảy nước mắt,ghèn mắt,nước mắt nhiều,viêm mắt,đỏ mắt',
 'Mắt đỏ, chảy ghèn, chảy nước mắt ở chim có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc, Nhiễm khuẩn đường hô hấp (Chlamydiosis), Dị vật trong mắt, Tắc ống lệ, Nhiễm trùng mắt, Chấn thương mắt. Follow-up: Có chảy nước mũi không? Có ho / khó thở không? Có một mắt hay cả hai mắt? Có sưng mắt không? Cần đưa chim đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 11. Mắt sưng / Mắt nhắm
('Mắt sưng, mắt nhắm ở chim',
 'mắt sưng,mắt bị sưng,sưng mắt,phù mắt,mí mắt sưng,mắt nhắm,nhắm mắt',
 'Mắt sưng hoặc mắt nhắm ở chim có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng mắt, Áp xe quanh mắt, Dị vật trong mắt, Chấn thương mắt, Nhiễm khuẩn đường hô hấp, Khối u quanh mắt. Follow-up: Có mắt đỏ không? Có chảy ghèn không? Có đau không? Có một mắt hay cả hai mắt? Cần đưa chim đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. THẦN KINH (NGUY HIỂM Ở CHIM)
-- =====================================================

-- 12. Co giật / Động kinh
('Co giật, động kinh ở chim',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân,run,run rẩy',
 'Co giật ở chim là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh, Nhiễm khuẩn đường hô hấp gây viêm não, Ngộ độc (kim loại nặng, khói thuốc), Chấn thương đầu, Khối u não, Bệnh PDD, Thiếu canxi. Follow-up: Có mất thăng bằng không? Có quay vòng vòng không? Có nghiêng đầu không? Trong cơn co giật, cần giữ chim an toàn và đưa đến bác sĩ thú y ngay sau cơn co giật.',
 'SEVERE',
 TRUE,
 NOW()),

-- 13. Mất thăng bằng / Quay vòng vòng
('Mất thăng bằng, quay vòng vòng ở chim',
 'mất thăng bằng,quay vòng vòng,quay tròn,đi loạng choạng,đi không vững,ngã',
 'Mất thăng bằng hoặc quay vòng vòng ở chim thường là dấu hiệu của các vấn đề về thần kinh hoặc tai trong. Nghi ngờ: Bệnh thần kinh, Chấn thương đầu, Khối u não, Nhiễm khuẩn đường hô hấp, Ngộ độc, Thiếu canxi, Bệnh PDD. Follow-up: Có co giật không? Có nghiêng đầu không? Có nôn không? Cần đưa chim đến bác sĩ thú y ngay để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 14. Nghiêng đầu / Đầu nghiêng một bên
('Nghiêng đầu, đầu nghiêng một bên ở chim',
 'nghiêng đầu,đầu nghiêng một bên,đầu nghiêng,nghiêng đầu liên tục',
 'Nghiêng đầu một bên ở chim có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh thần kinh, Chấn thương đầu, Khối u não, Nhiễm khuẩn đường hô hấp, Ngộ độc, Thiếu canxi, Bệnh PDD, Vấn đề về tai trong. Follow-up: Có mất thăng bằng không? Có quay vòng vòng không? Có co giật không? Cần đưa chim đến bác sĩ thú y ngay để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. TRIỆU CHỨNG CHUNG
-- =====================================================

-- 15. Mệt mỏi / Lờ đờ / Sụt cân
('Mệt mỏi, lờ đờ, sụt cân ở chim',
 'mệt mỏi,lờ đờ,yếu,kiệt sức,không hoạt động,không bay,ngủ nhiều,sụt cân,gầy nhanh',
 'Mệt mỏi, lờ đờ, sụt cân ở chim có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng đường hô hấp, Nhiễm khuẩn đường ruột, Bệnh PDD, Stress, Ngộ độc, Bệnh tim, Thiếu dinh dưỡng, Bệnh gan. Follow-up: Có bỏ ăn không? Có ho / khó thở không? Có tiêu chảy không? Có sốt không? Cần đưa chim đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_bird ORDER BY created_date DESC LIMIT 15;
