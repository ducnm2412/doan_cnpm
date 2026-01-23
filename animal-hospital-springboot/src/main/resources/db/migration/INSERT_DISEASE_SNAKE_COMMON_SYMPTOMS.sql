-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_snake
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_snake (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. HÔ HẤP (RẤT NGUY HIỂM Ở RẮN)
-- =====================================================

-- 1. Khó thở / Thở há miệng / Thở gấp
('Khó thở, thở há miệng, thở gấp ở rắn',
 'khó thở,thở há miệng,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở bằng miệng,thở há miệng liên tục',
 'Khó thở, thở há miệng ở rắn là dấu hiệu nghiêm trọng của bệnh hô hấp. Nghi ngờ: Viêm phổi (Pneumonia), Nhiễm trùng đường hô hấp (RI - Respiratory Infection), Bệnh hô hấp do vi khuẩn, Dị vật đường thở, Nhiễm trùng đường hô hấp trên. Follow-up: Có chảy nước mũi không? Có sưng miệng không? Có bỏ ăn không? Có nổi bọt ở miệng không? Khó thở ở rắn là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 2. Chảy nước mũi / Sổ mũi / Nước mũi đặc
('Chảy nước mũi, sổ mũi, nước mũi đặc ở rắn',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều,nước mũi có bọt',
 'Chảy nước mũi, sổ mũi ở rắn thường là dấu hiệu của nhiễm trùng đường hô hấp nghiêm trọng. Nghi ngờ: Nhiễm trùng đường hô hấp (RI - Respiratory Infection), Viêm phổi, Nhiễm khuẩn, Nhiễm virus, Nhiệt độ môi trường không phù hợp. Follow-up: Có khó thở không? Có thở há miệng không? Có bỏ ăn không? Có sưng miệng không? Cần đưa rắn đến bác sĩ thú y ngay để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'SEVERE',
 TRUE,
 NOW()),

-- 3. Nổi bọt ở miệng / Bọt ở miệng
('Nổi bọt ở miệng, bọt ở miệng ở rắn',
 'nổi bọt ở miệng,bọt ở miệng,miệng có bọt,chảy bọt ở miệng,bọt trắng ở miệng',
 'Nổi bọt ở miệng ở rắn là dấu hiệu nghiêm trọng của bệnh hô hấp hoặc nhiễm trùng. Nghi ngờ: Viêm phổi nặng, Nhiễm trùng đường hô hấp nghiêm trọng, Nhiễm khuẩn đường hô hấp, Bệnh hô hấp cấp tính. Follow-up: Có khó thở không? Có thở há miệng không? Có chảy nước mũi không? Có bỏ ăn không? Đây là trường hợp khẩn cấp, cần đưa rắn đến bác sĩ thú y ngay lập tức.',
 'CRITICAL',
 TRUE,
 NOW()),

-- =====================================================
-- 2. MIỆNG (RẤT QUAN TRỌNG Ở RẮN)
-- =====================================================

-- 4. Loét miệng / Vết loét trong miệng
('Loét miệng, vết loét trong miệng ở rắn',
 'loét miệng,vết loét trong miệng,loét trong miệng,vết loét miệng,miệng bị loét,loét nướu',
 'Loét miệng ở rắn có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng miệng (Stomatitis), Nhiễm khuẩn miệng, Bệnh răng miệng, Nhiễm virus, Chấn thương miệng. Follow-up: Có chảy nước dãi không? Có bỏ ăn không? Có sưng miệng không? Có mùi hôi từ miệng không? Cần đưa rắn đến bác sĩ thú y ngay để điều trị bằng thuốc kháng sinh và làm sạch vết loét.',
 'SEVERE',
 TRUE,
 NOW()),

-- 5. Sưng miệng / Miệng phình
('Sưng miệng, miệng phình ở rắn',
 'sưng miệng,miệng phình,miệng sưng,miệng to,miệng phù,miệng bị sưng',
 'Sưng miệng, miệng phình ở rắn có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Áp xe miệng, Nhiễm trùng miệng (Stomatitis), Khối u, Nhiễm khuẩn, Chấn thương miệng. Follow-up: Có loét miệng không? Có chảy nước dãi không? Có bỏ ăn không? Có khó thở không? Cần đưa rắn đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- 6. Chảy nước dãi / Nước dãi nhiều
('Chảy nước dãi, nước dãi nhiều ở rắn',
 'chảy nước dãi,nước dãi nhiều,chảy dãi,tiết nước bọt nhiều,miệng chảy nước dãi',
 'Chảy nước dãi nhiều ở rắn thường là dấu hiệu của bệnh miệng nghiêm trọng. Nghi ngờ: Nhiễm trùng miệng (Stomatitis), Loét miệng, Nhiễm khuẩn miệng, Khó nuốt, Dị vật trong miệng. Follow-up: Có loét miệng không? Có sưng miệng không? Có bỏ ăn không? Có khó thở không? Cần đưa rắn đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. TIÊU HÓA
-- =====================================================

-- 7. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở rắn',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn,từ chối thức ăn',
 'Bỏ ăn ở rắn có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng đường hô hấp, Bệnh tiêu hóa, Tắc nghẽn đường ruột, Ngộ độc, Stress, Nhiệt độ môi trường không phù hợp, Nhiễm trùng miệng. Follow-up: Có khó thở không? Có nôn không? Có táo bón không? Có sưng miệng không? Có loét miệng không? Nếu rắn bỏ ăn quá 2-3 tuần, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 8. Nôn / Ói / Nôn ra thức ăn
('Nôn, ói, nôn ra thức ăn ở rắn',
 'nôn,ói,nôn ra thức ăn,buồn nôn,nôn mửa,ói khan,nôn ngược',
 'Nôn ở rắn là triệu chứng nghiêm trọng, đặc biệt nếu nôn ngược (regurgitation). Nghi ngờ: Tắc nghẽn đường ruột, Nuốt dị vật, Ngộ độc, Nhiễm trùng đường tiêu hóa, Bệnh đường ruột, Nhiệt độ môi trường không phù hợp. Follow-up: Có bỏ ăn không? Có táo bón không? Có bụng chướng không? Có khó thở không? Nôn ở rắn là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 9. Táo bón / Không đi ngoài / Không đi tiêu
('Táo bón, không đi ngoài, không đi tiêu ở rắn',
 'táo bón,không đi ngoài,không đi tiêu,không đi cầu,rặn,đi ngoài khó,phân cứng',
 'Táo bón hoặc không đi ngoài ở rắn là dấu hiệu nghiêm trọng. Nghi ngờ: Tắc nghẽn đường ruột, Nuốt dị vật, Thiếu nước, Nhiệt độ môi trường không phù hợp, Bệnh đường ruột, Nhiễm ký sinh trùng. Follow-up: Có bỏ ăn không? Có nôn không? Có bụng chướng không? Có đau không? Nếu rắn không đi ngoài quá 2-3 tuần sau khi ăn, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. DA VÀ LỘT DA (RẤT QUAN TRỌNG Ở RẮN)
-- =====================================================

-- 10. Lột da không đúng cách / Da còn sót lại
('Lột da không đúng cách, da còn sót lại ở rắn',
 'lột da không đúng cách,da còn sót lại,da không lột hết,da còn dính,da còn trên mắt,da còn trên đuôi,lột da khó',
 'Lột da không đúng cách ở rắn là dấu hiệu của vấn đề sức khỏe. Nghi ngờ: Thiếu độ ẩm, Nhiệt độ môi trường không phù hợp, Thiếu vitamin, Nhiễm trùng da, Bệnh da, Suy dinh dưỡng. Follow-up: Có da còn trên mắt không? Có da còn trên đuôi không? Có nhiễm trùng da không? Có bỏ ăn không? Cần cải thiện môi trường sống và đưa rắn đến bác sĩ thú y nếu da còn sót lại quá nhiều.',
 'MODERATE',
 TRUE,
 NOW()),

-- 11. Da khô / Vảy khô / Vảy bong tróc
('Da khô, vảy khô, vảy bong tróc ở rắn',
 'da khô,vảy khô,vảy bong tróc,vảy tróc,vảy khô cứng,da không mềm',
 'Da khô, vảy khô ở rắn có thể do nhiều nguyên nhân. Nghi ngờ: Thiếu độ ẩm, Nhiệt độ môi trường không phù hợp, Thiếu nước, Nhiễm trùng da, Bệnh da, Suy dinh dưỡng. Follow-up: Có lột da không đúng cách không? Có nhiễm trùng da không? Có bỏ ăn không? Cần cải thiện môi trường sống và đưa rắn đến bác sĩ thú y để kiểm tra.',
 'MILD',
 TRUE,
 NOW()),

-- 12. Nhiễm trùng da / Da đỏ / Viêm da
('Nhiễm trùng da, da đỏ, viêm da ở rắn',
 'nhiễm trùng da,da đỏ,viêm da,da sưng,da bị đỏ,da viêm,da kích ứng,vết thương trên da',
 'Nhiễm trùng da, da đỏ, viêm da ở rắn có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn da, Nhiễm nấm, Chấn thương da, Môi trường sống không sạch sẽ, Nhiễm ký sinh trùng, Vết thương không lành. Follow-up: Có vết thương không? Có chảy dịch không? Có mùi hôi không? Có bỏ ăn không? Cần đưa rắn đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. MẮT
-- =====================================================

-- 13. Mắt đục / Mắt trắng đục / Mắt mờ
('Mắt đục, mắt trắng đục, mắt mờ ở rắn',
 'mắt đục,mắt trắng đục,mắt mờ,mắt bị đục,mắt trắng,mắt không trong',
 'Mắt đục, mắt trắng đục ở rắn có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng mắt, Viêm mắt, Nhiễm khuẩn mắt, Bệnh mắt, Thiếu vitamin, Nhiễm trùng đường hô hấp. Follow-up: Có mắt đóng không? Có sưng mắt không? Có chảy nước mắt không? Có khó thở không? Cần đưa rắn đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 14. Mắt đóng / Mắt không mở được / Mắt dính
('Mắt đóng, mắt không mở được, mắt dính ở rắn',
 'mắt đóng,mắt không mở được,mắt dính,mắt nhắm,mắt không mở,mắt bị dính',
 'Mắt đóng, mắt không mở được ở rắn thường do da còn sót lại sau khi lột da hoặc nhiễm trùng. Nghi ngờ: Da còn sót lại trên mắt, Nhiễm trùng mắt, Viêm mắt, Nhiễm khuẩn mắt, Bệnh mắt. Follow-up: Có lột da không đúng cách không? Có mắt đục không? Có chảy nước mắt không? Có khó thở không? Cần đưa rắn đến bác sĩ thú y để kiểm tra và loại bỏ da còn sót lại hoặc điều trị nhiễm trùng.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. HÀNH VI VÀ THẦN KINH
-- =====================================================

-- 15. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở rắn',
 'lờ đờ,mệt mỏi,không hoạt động,không di chuyển,ngủ nhiều,không phản ứng,không cử động',
 'Lờ đờ, mệt mỏi ở rắn có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Ngộ độc, Nhiệt độ môi trường không phù hợp, Suy dinh dưỡng, Bệnh thần kinh. Follow-up: Có bỏ ăn không? Có khó thở không? Có nôn không? Có co giật không? Có nhiệt độ môi trường phù hợp không? Cần đưa rắn đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_snake ORDER BY created_date DESC LIMIT 15;
