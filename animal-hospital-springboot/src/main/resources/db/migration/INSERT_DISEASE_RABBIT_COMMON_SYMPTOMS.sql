-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_rabbit
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_rabbit (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. TIÊU HÓA (RẤT HAY GẶP Ở THỎ)
-- =====================================================

-- 1. Tiêu chảy / Đi phân lỏng
('Tiêu chảy, đi phân lỏng ở thỏ',
 'tiêu chảy,đi phân lỏng,đi ngoài lỏng,phân lỏng,đi cầu lỏng,ỉa chảy,phân không thành khuôn',
 'Tiêu chảy là triệu chứng rất nguy hiểm ở thỏ và có thể gây tử vong nhanh chóng. Nghi ngờ: Bệnh viêm đường ruột (Enteritis), Nhiễm khuẩn đường ruột (E.coli, Clostridium), Coccidiosis (bệnh cầu trùng), Thay đổi thức ăn đột ngột, Ngộ độc thức ăn, Bệnh răng miệng gây đau. Follow-up: Có nôn không? Có bỏ ăn không? Có bụng chướng không? Có phân có máu không? Thỏ tiêu chảy cần được đưa đến bác sĩ thú y ngay lập tức vì có thể mất nước và tử vong rất nhanh.',
 'SEVERE',
 TRUE,
 NOW()),

-- 2. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở thỏ',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở thỏ là dấu hiệu cực kỳ nghiêm trọng vì thỏ cần ăn liên tục để hệ tiêu hóa hoạt động. Nếu thỏ không ăn quá 12 giờ có thể gây tắc nghẽn đường ruột và tử vong. Nghi ngờ: Bệnh răng miệng (răng mọc quá dài, áp xe răng), Tắc nghẽn đường ruột (GI Stasis), Đau đớn, Stress, Nhiễm trùng, Bệnh đường tiêu hóa. Follow-up: Có không đi ngoài được không? Có bụng chướng không? Có chảy nước dãi không? Có khó nhai không? Nếu thỏ bỏ ăn quá 12 giờ, cần đưa đến bác sĩ thú y ngay lập tức.',
 'CRITICAL',
 TRUE,
 NOW()),

-- 3. Bụng chướng / Bụng phình
('Bụng chướng, bụng phình ở thỏ',
 'bụng chướng,bụng phình,bụng to,bụng căng,chướng bụng,phình bụng,bụng cứng',
 'Bụng chướng ở thỏ là tình trạng khẩn cấp rất nguy hiểm, có thể gây tử vong trong vài giờ. Nghi ngờ: Tắc nghẽn đường ruột (GI Stasis), Nuốt dị vật, Bệnh răng miệng gây đau không ăn được, Thay đổi thức ăn đột ngột, Nhiễm khuẩn đường ruột. Follow-up: Có bỏ ăn không? Có không đi ngoài được không? Có đau không? Có nôn không? Bụng chướng ở thỏ là trường hợp cấp cứu, cần đưa đến bác sĩ thú y ngay lập tức.',
 'CRITICAL',
 TRUE,
 NOW()),

-- 4. Táo bón / Không đi ngoài
('Táo bón, không đi ngoài ở thỏ',
 'táo bón,không đi ngoài,rặn,đi ngoài khó,phân cứng,không đi cầu được',
 'Táo bón hoặc không đi ngoài ở thỏ là dấu hiệu nghiêm trọng của tắc nghẽn đường ruột. Nghi ngờ: Tắc nghẽn đường ruột (GI Stasis), Nuốt dị vật, Bệnh răng miệng gây đau không ăn được, Thiếu nước, Thiếu chất xơ trong thức ăn. Follow-up: Có bỏ ăn không? Có bụng chướng không? Có đau không? Có chảy nước dãi không? Nếu thỏ không đi ngoài quá 12 giờ, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. HÔ HẤP
-- =====================================================

-- 5. Ho / Khó thở / Thở gấp
('Ho, khó thở, thở gấp ở thỏ',
 'ho,ho khan,khó thở,thở gấp,thở nhanh,thở khó,thở há miệng,thở nặng nhọc',
 'Ho, khó thở, thở gấp ở thỏ có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Bệnh hô hấp (Pasteurellosis - bệnh nhiễm khuẩn Pasteurella), Viêm phổi, Viêm phế quản, Dị vật đường thở, Bệnh tim, Stress nhiệt. Follow-up: Có chảy nước mũi không? Có chảy nước mắt không? Có sốt không? Có tím lưỡi / lợi không? Khó thở ở thỏ là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 6. Chảy nước mũi / Sổ mũi
('Chảy nước mũi, sổ mũi ở thỏ',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều',
 'Chảy nước mũi, sổ mũi ở thỏ thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Bệnh hô hấp (Pasteurellosis), Viêm phổi, Viêm phế quản, Nhiễm khuẩn đường hô hấp, Dị ứng. Follow-up: Có ho không? Có khó thở không? Có sốt không? Có bỏ ăn không? Cần đưa thỏ đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. DA LÔNG
-- =====================================================

-- 7. Ngứa / Gãi nhiều / Rụng lông
('Ngứa, gãi nhiều, rụng lông ở thỏ',
 'ngứa,gãi nhiều,gãi liên tục,ngứa ngáy,ngứa da,rụng lông,hói,rụng lông nhiều,lông rụng',
 'Ngứa, gãi nhiều, rụng lông ở thỏ có thể do nhiều nguyên nhân. Nghi ngờ: Nấm da (Ringworm), Ve, rận, bọ chét, Viêm da dị ứng, Nhiễm ký sinh trùng ngoài da, Rối loạn nội tiết, Stress. Follow-up: Có da đỏ / viêm da không? Có vảy / gàu không? Có mụn / ghẻ không? Có mùi hôi không? Cần đưa thỏ đến bác sĩ thú y để kiểm tra và điều trị. Một số bệnh có thể lây sang người.',
 'MODERATE',
 TRUE,
 NOW()),

-- 8. Da đỏ / Viêm da / Mụn
('Da đỏ, viêm da, mụn ở thỏ',
 'da đỏ,viêm da,da bị đỏ,viêm da mủ,da sưng đỏ,mụn,mụn mủ,mụn trên da',
 'Da đỏ, viêm da, mụn ở thỏ có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn da, Nấm da, Viêm da dị ứng, Ký sinh trùng ngoài da, Tự nhổ lông gây tổn thương da, Áp xe da. Follow-up: Có ngứa / gãi nhiều không? Có chảy mủ không? Có sưng không? Có mùi hôi không? Cần đưa thỏ đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. MIỆNG - RĂNG (RẤT QUAN TRỌNG Ở THỎ)
-- =====================================================

-- 9. Chảy nước dãi / Khó nhai
('Chảy nước dãi, khó nhai ở thỏ',
 'chảy nước dãi,nước dãi nhiều,chảy dãi,tiết nước bọt nhiều,khó nhai,nhai một bên,nhai khó,nhai đau',
 'Chảy nước dãi và khó nhai ở thỏ thường là dấu hiệu của bệnh răng miệng nghiêm trọng. Răng thỏ mọc liên tục và có thể mọc quá dài gây đau. Nghi ngờ: Răng mọc quá dài (Malocclusion), Áp xe răng, Loét miệng, Dị vật trong miệng, Nhiễm trùng răng miệng. Follow-up: Có bỏ ăn không? Có hôi miệng không? Có sưng mặt không? Có chảy máu miệng không? Cần đưa thỏ đến bác sĩ thú y ngay để kiểm tra và mài răng nếu cần.',
 'SEVERE',
 TRUE,
 NOW()),

-- 10. Hôi miệng
('Hôi miệng ở thỏ',
 'hôi miệng,miệng hôi,mùi hôi miệng,mùi khó chịu ở miệng',
 'Hôi miệng ở thỏ thường do vấn đề về răng miệng hoặc nhiễm trùng. Nghi ngờ: Áp xe răng, Nhiễm trùng răng miệng, Răng mọc quá dài gây loét, Viêm nướu, Bệnh đường tiêu hóa. Follow-up: Có chảy nước dãi không? Có khó nhai không? Có bỏ ăn không? Có sưng mặt không? Cần kiểm tra răng miệng tại phòng khám thú y để xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. MẮT VÀ TAI
-- =====================================================

-- 11. Mắt đỏ / Chảy ghèn / Chảy nước mắt
('Mắt đỏ, chảy ghèn, chảy nước mắt ở thỏ',
 'mắt đỏ,chảy ghèn,chảy nước mắt,ghèn mắt,nước mắt nhiều,viêm mắt,đỏ mắt',
 'Mắt đỏ, chảy ghèn, chảy nước mắt ở thỏ có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc, Nhiễm khuẩn Pasteurella (Pasteurellosis), Dị vật trong mắt, Tắc ống lệ, Bệnh răng miệng (răng mọc quá dài chèn ép ống lệ). Follow-up: Có chảy nước mũi không? Có ho / khó thở không? Có sưng mặt không? Có một mắt hay cả hai mắt? Cần đưa thỏ đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 12. Hôi tai / Ráy tai nhiều / Tai đỏ
('Hôi tai, ráy tai nhiều, tai đỏ ở thỏ',
 'hôi tai,tai hôi,ráy tai nhiều,ráy tai đen,tai đỏ,tai sưng,mùi hôi tai',
 'Hôi tai, ráy tai nhiều, tai đỏ ở thỏ thường là dấu hiệu của nhiễm trùng tai. Nghi ngờ: Viêm tai giữa, Nhiễm khuẩn Pasteurella, Ve tai, Nhiễm trùng tai do vi khuẩn hoặc nấm. Follow-up: Có lắc đầu / nghiêng đầu không? Có gãi tai không? Có chảy dịch tai không? Có mất thăng bằng không? Cần đưa thỏ đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh hoặc kháng viêm.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. TIẾT NIỆU VÀ THẦN KINH
-- =====================================================

-- 13. Tiểu ra máu / Khó tiểu
('Tiểu ra máu, khó tiểu ở thỏ',
 'tiểu ra máu,nước tiểu có máu,tiểu máu,máu trong nước tiểu,đái ra máu,khó tiểu,tiểu khó,không tiểu được',
 'Tiểu ra máu hoặc khó tiểu ở thỏ là dấu hiệu nghiêm trọng. Nghi ngờ: Sỏi bàng quang, Nhiễm trùng đường tiết niệu (UTI), Ung thư đường tiết niệu, Bệnh thận, Chấn thương. Follow-up: Có tiểu rắt không? Có liếm vùng kín nhiều không? Có đau không? Có bụng chướng không? Cần đưa thỏ đến bác sĩ thú y ngay để kiểm tra và điều trị. Có thể cần siêu âm hoặc chụp X-quang.',
 'SEVERE',
 TRUE,
 NOW()),

-- 14. Co giật / Động kinh
('Co giật, động kinh ở thỏ',
 'co giật,động kinh,lên cơn co giật,co giật toàn thân,run,run rẩy',
 'Co giật ở thỏ là dấu hiệu nghiêm trọng. Nghi ngờ: Bệnh thần kinh, Nhiễm khuẩn Pasteurella gây viêm não, Ngộ độc, Chấn thương đầu, U não, Bệnh cột sống. Follow-up: Có mất thăng bằng không? Có quay vòng vòng không? Có nghiêng đầu không? Có nôn không? Trong cơn co giật, cần giữ thỏ an toàn và đưa đến bác sĩ thú y ngay sau cơn co giật để kiểm tra.',
 'SEVERE',
 TRUE,
 NOW()),

-- 15. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở thỏ',
 'lờ đờ,mệt mỏi,không hoạt động,không di chuyển,ngủ nhiều,không phản ứng,kiệt sức',
 'Lờ đờ, mệt mỏi ở thỏ có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Sốt, Ngộ độc, Thiếu máu, Suy dinh dưỡng, Bệnh răng miệng. Follow-up: Có bỏ ăn không? Có sốt không? Có nôn / tiêu chảy không? Có khó thở không? Có khó nhai không? Cần đưa thỏ đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_rabbit ORDER BY created_date DESC LIMIT 15;
