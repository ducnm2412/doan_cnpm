-- =====================================================
-- Script INSERT các keyword triệu chứng phổ biến vào bảng disease_turtle
-- =====================================================

USE animalhospital;

-- Insert các triệu chứng phổ biến với keywords
INSERT INTO disease_turtle (title, keywords, content, severity_level, is_active, created_date) VALUES

-- =====================================================
-- 1. MAI (RẤT QUAN TRỌNG Ở RÙA)
-- =====================================================

-- 1. Mềm mai / Mai mềm
('Mềm mai, mai mềm ở rùa',
 'mềm mai,mai mềm,mai mềm nhũn,mai không cứng,mai yếu,mai mỏng',
 'Mềm mai là triệu chứng nghiêm trọng ở rùa, thường do thiếu canxi và vitamin D3. Nghi ngờ: Bệnh mềm mai (Metabolic Bone Disease - MBD), Thiếu canxi, Thiếu vitamin D3, Thiếu ánh sáng UVB, Suy dinh dưỡng, Bệnh thận. Follow-up: Có bỏ ăn không? Có sưng chân không? Có khó di chuyển không? Có sưng mắt không? Rùa mềm mai cần được đưa đến bác sĩ thú y ngay để điều trị bằng bổ sung canxi và vitamin D3, đồng thời cải thiện môi trường sống.',
 'SEVERE',
 TRUE,
 NOW()),

-- 2. Loét mai / Vết loét trên mai
('Loét mai, vết loét trên mai ở rùa',
 'loét mai,vết loét trên mai,mai bị loét,mai có vết thương,mai bị tổn thương,mai bị nhiễm trùng',
 'Loét mai là dấu hiệu của nhiễm trùng nghiêm trọng. Nghi ngờ: Nhiễm trùng mai (Shell Rot), Nhiễm nấm, Nhiễm khuẩn, Chấn thương mai, Môi trường sống không sạch sẽ. Follow-up: Có mùi hôi từ mai không? Có chảy dịch không? Có mềm mai không? Có bỏ ăn không? Cần đưa rùa đến bác sĩ thú y ngay để điều trị bằng thuốc kháng sinh và làm sạch vết loét.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 2. MẮT (RẤT PHỔ BIẾN Ở RÙA)
-- =====================================================

-- 3. Sưng mắt / Mắt đóng / Mắt không mở được
('Sưng mắt, mắt đóng, mắt không mở được ở rùa',
 'sưng mắt,mắt đóng,mắt không mở được,mắt sưng,mắt phù,mắt nhắm,mắt không mở',
 'Sưng mắt, mắt đóng là triệu chứng rất phổ biến ở rùa, thường do thiếu vitamin A. Nghi ngờ: Thiếu vitamin A (Hypovitaminosis A), Nhiễm trùng mắt, Viêm kết mạc, Nhiễm khuẩn, Môi trường sống không phù hợp. Follow-up: Có bỏ ăn không? Có chảy nước mũi không? Có khó thở không? Có sưng cổ không? Cần đưa rùa đến bác sĩ thú y để điều trị bằng bổ sung vitamin A và thuốc kháng sinh nếu có nhiễm trùng.',
 'MODERATE',
 TRUE,
 NOW()),

-- 4. Mắt đỏ / Chảy ghèn mắt
('Mắt đỏ, chảy ghèn mắt ở rùa',
 'mắt đỏ,chảy ghèn mắt,ghèn mắt,mắt viêm,đỏ mắt,chảy nước mắt',
 'Mắt đỏ, chảy ghèn mắt ở rùa có thể do nhiều nguyên nhân. Nghi ngờ: Viêm kết mạc, Nhiễm trùng mắt, Thiếu vitamin A, Nhiễm khuẩn, Dị vật trong mắt. Follow-up: Có sưng mắt không? Có mắt đóng không? Có bỏ ăn không? Cần đưa rùa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 3. HÔ HẤP (RẤT NGUY HIỂM Ở RÙA)
-- =====================================================

-- 5. Khó thở / Thở há miệng / Thở gấp
('Khó thở, thở há miệng, thở gấp ở rùa',
 'khó thở,thở há miệng,thở gấp,thở nhanh,thở khó,thở nặng nhọc,thở bằng miệng',
 'Khó thở, thở há miệng ở rùa là dấu hiệu nghiêm trọng của bệnh hô hấp. Nghi ngờ: Viêm phổi (Pneumonia), Nhiễm trùng đường hô hấp, Bệnh hô hấp do vi khuẩn, Dị vật đường thở, Nhiễm trùng đường hô hấp trên (URI). Follow-up: Có chảy nước mũi không? Có sưng cổ không? Có bỏ ăn không? Có nổi lềnh bềnh không? Khó thở ở rùa là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 6. Chảy nước mũi / Sổ mũi / Nước mũi đặc
('Chảy nước mũi, sổ mũi, nước mũi đặc ở rùa',
 'chảy nước mũi,sổ mũi,nước mũi đặc,nước mũi vàng,nước mũi xanh,sổ mũi nhiều',
 'Chảy nước mũi, sổ mũi ở rùa thường là dấu hiệu của nhiễm trùng đường hô hấp. Nghi ngờ: Nhiễm trùng đường hô hấp trên (URI), Viêm phổi, Nhiễm khuẩn, Nhiễm virus, Thiếu vitamin A. Follow-up: Có khó thở không? Có thở há miệng không? Có bỏ ăn không? Có sưng mắt không? Cần đưa rùa đến bác sĩ thú y để kiểm tra và điều trị bằng thuốc kháng sinh.',
 'MODERATE',
 TRUE,
 NOW()),

-- 7. Sưng cổ / Cổ phình
('Sưng cổ, cổ phình ở rùa',
 'sưng cổ,cổ phình,cổ sưng,cổ to,cổ phù,cổ bị sưng',
 'Sưng cổ, cổ phình ở rùa có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Áp xe cổ, Nhiễm trùng đường hô hấp, Viêm phổi, Khối u, Nhiễm trùng tai giữa. Follow-up: Có khó thở không? Có chảy nước mũi không? Có bỏ ăn không? Có sưng mắt không? Cần đưa rùa đến bác sĩ thú y ngay để kiểm tra và điều trị.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 4. TIÊU HÓA
-- =====================================================

-- 8. Bỏ ăn / Biếng ăn / Ăn ít
('Bỏ ăn, biếng ăn, ăn ít ở rùa',
 'bỏ ăn,biếng ăn,ăn ít,không ăn,chán ăn,không thèm ăn,ăn kém,không chịu ăn',
 'Bỏ ăn ở rùa có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng đường hô hấp, Bệnh tiêu hóa, Tắc nghẽn đường ruột, Ngộ độc, Stress, Nhiệt độ môi trường không phù hợp, Thiếu ánh sáng UVB. Follow-up: Có khó thở không? Có tiêu chảy không? Có nôn không? Có mềm mai không? Có sưng mắt không? Nếu rùa bỏ ăn quá 1 tuần, cần đưa đến bác sĩ thú y ngay.',
 'MODERATE',
 TRUE,
 NOW()),

-- 9. Tiêu chảy / Phân lỏng / Phân không thành khuôn
('Tiêu chảy, phân lỏng, phân không thành khuôn ở rùa',
 'tiêu chảy,phân lỏng,đi phân lỏng,phân không thành khuôn,phân nước,đi ngoài lỏng',
 'Tiêu chảy ở rùa có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm khuẩn đường ruột, Ký sinh trùng đường ruột, Thay đổi thức ăn đột ngột, Ngộ độc thức ăn, Bệnh đường ruột, Nhiễm trùng đường tiêu hóa. Follow-up: Có bỏ ăn không? Có phân có máu không? Có nôn không? Có mềm mai không? Nếu tiêu chảy kéo dài hoặc kèm máu, cần đưa rùa đến bác sĩ thú y.',
 'MODERATE',
 TRUE,
 NOW()),

-- 10. Nôn / Ói
('Nôn, ói ở rùa',
 'nôn,ói,ói ra thức ăn,buồn nôn,nôn mửa,ói khan',
 'Nôn ở rùa là triệu chứng nghiêm trọng. Nghi ngờ: Tắc nghẽn đường ruột, Nuốt dị vật, Ngộ độc, Nhiễm trùng đường tiêu hóa, Bệnh đường ruột. Follow-up: Có bỏ ăn không? Có tiêu chảy không? Có bụng chướng không? Có khó thở không? Nôn ở rùa là trường hợp khẩn cấp, cần đưa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- =====================================================
-- 5. DA VÀ VẢY
-- =====================================================

-- 11. Rụng vảy / Vảy bong tróc / Vảy rơi nhiều
('Rụng vảy, vảy bong tróc, vảy rơi nhiều ở rùa',
 'rụng vảy,vảy bong tróc,vảy rơi nhiều,vảy tróc,vảy bong,vảy rụng,vảy lột',
 'Rụng vảy ở rùa có thể là bình thường khi lột da, nhưng nếu quá nhiều hoặc không đúng cách thì là bệnh lý. Nghi ngờ: Nhiễm nấm da, Nhiễm khuẩn da, Thiếu vitamin, Môi trường sống không phù hợp, Nhiễm trùng da. Follow-up: Có da đỏ / viêm da không? Có mùi hôi không? Có loét mai không? Có bỏ ăn không? Cần đưa rùa đến bác sĩ thú y để kiểm tra.',
 'MILD',
 TRUE,
 NOW()),

-- 12. Da đỏ / Viêm da / Da sưng
('Da đỏ, viêm da, da sưng ở rùa',
 'da đỏ,viêm da,da sưng,da bị đỏ,da viêm,da kích ứng',
 'Da đỏ, viêm da ở rùa có thể do nhiều nguyên nhân. Nghi ngờ: Nhiễm trùng da, Nhiễm nấm, Nhiễm khuẩn, Dị ứng, Môi trường sống không sạch sẽ, Nhiễm ký sinh trùng. Follow-up: Có rụng vảy không? Có mùi hôi không? Có loét mai không? Có ngứa / gãi nhiều không? Cần đưa rùa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- =====================================================
-- 6. CHÂN VÀ DI CHUYỂN
-- =====================================================

-- 13. Sưng chân / Chân phình / Chân to
('Sưng chân, chân phình, chân to ở rùa',
 'sưng chân,chân phình,chân to,chân sưng,chân phù,chân bị sưng',
 'Sưng chân ở rùa có thể do nhiều nguyên nhân. Nghi ngờ: Bệnh mềm mai (MBD), Nhiễm trùng khớp, Gout, Chấn thương, Nhiễm trùng da, Áp xe. Follow-up: Có mềm mai không? Có khó di chuyển không? Có đau không? Có bỏ ăn không? Cần đưa rùa đến bác sĩ thú y để kiểm tra và điều trị.',
 'MODERATE',
 TRUE,
 NOW()),

-- 14. Không bơi được / Nổi lềnh bềnh / Nghiêng một bên khi bơi
('Không bơi được, nổi lềnh bềnh, nghiêng một bên khi bơi ở rùa',
 'không bơi được,nổi lềnh bềnh,nghiêng một bên khi bơi,bơi nghiêng,bơi không đều,chìm xuống đáy',
 'Không bơi được hoặc nổi lềnh bềnh ở rùa là dấu hiệu nghiêm trọng. Nghi ngờ: Viêm phổi (Pneumonia), Nhiễm trùng đường hô hấp, Tích tụ khí trong phổi, Bệnh hô hấp, Nhiễm trùng. Follow-up: Có khó thở không? Có thở há miệng không? Có chảy nước mũi không? Có bỏ ăn không? Đây là trường hợp khẩn cấp, cần đưa rùa đến bác sĩ thú y ngay.',
 'SEVERE',
 TRUE,
 NOW()),

-- 15. Lờ đờ / Mệt mỏi / Không hoạt động
('Lờ đờ, mệt mỏi, không hoạt động ở rùa',
 'lờ đờ,mệt mỏi,không hoạt động,không di chuyển,ngủ nhiều,không phản ứng',
 'Lờ đờ, mệt mỏi ở rùa có thể do nhiều nguyên nhân nghiêm trọng. Nghi ngờ: Nhiễm trùng, Bệnh hô hấp, Bệnh tiêu hóa, Ngộ độc, Nhiệt độ môi trường không phù hợp, Thiếu ánh sáng UVB, Suy dinh dưỡng. Follow-up: Có bỏ ăn không? Có khó thở không? Có mềm mai không? Có sưng mắt không? Cần đưa rùa đến bác sĩ thú y để kiểm tra và xác định nguyên nhân.',
 'MODERATE',
 TRUE,
 NOW());

-- Kiểm tra dữ liệu đã insert
SELECT id, title, keywords, severity_level FROM disease_turtle ORDER BY created_date DESC LIMIT 15;
