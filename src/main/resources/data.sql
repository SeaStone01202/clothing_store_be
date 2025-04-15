-- Thêm dữ liệu vào bảng categories
INSERT INTO categories (name, status, created_at) VALUES
                                                      ('Áo', 'ACTIVE', '2025-03-23'),
                                                      ('Quần', 'ACTIVE', '2025-03-23'),
                                                      ('Giày', 'ACTIVE', '2025-03-23'),
                                                      ('Phụ Kiện', 'ACTIVE', '2025-03-23');

-- Thêm dữ liệu vào bảng products
-- Category: Áo (category_id = 1)
INSERT INTO products (name, price, stock, description, image_url, category_id) VALUES
                                                                                   ('Áo thun nam cổ tròn', 150000, 100, 'Áo thun nam cổ tròn, chất liệu cotton thoáng mát', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/January2024/AT.220.xd.10.jpg', 1),
                                                                                   ('Áo sơ mi nam dài tay', 250000, 80, 'Áo sơ mi nam dài tay, phong cách lịch lãm', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/February2025/ao-so-mi-dai-tay-co-tau-premium-poplin-mau-be_(2).jpg', 1),
                                                                                   ('Áo polo nam cao cấp', 200000, 120, 'Áo polo nam, chất liệu cao cấp, thoải mái', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/December2024/24CM.MAWCS.PLZ613---.XANH3D.jpg', 1),
                                                                                   ('Áo khoác nam thể thao', 350000, 60, 'Áo khoác nam phong cách thể thao, chống thấm nước', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/November2024/24CMAW.KM010_-_xam_69.jpg', 1),
                                                                                   ('Áo hoodie nam unisex', 300000, 90, 'Áo hoodie nam unisex, phong cách trẻ trung', 'https://product.hstatic.net/1000360022/product/set-do-icondenim-new-york-1__3__3f53762d3f404d2395807a9d1d8b73f4_1024x1024.jpg', 1);

-- Category: Quần (category_id = 2)
INSERT INTO products (name, price, stock, description, image_url, category_id) VALUES
                                                                                   ('Quần jeans nam slim fit', 400000, 70, 'Quần jeans nam slim fit, phong cách hiện đại', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/December2024/quan-jeans-nam-basics-dang-slim-fit-den-wash-2_91.jpg', 2),
                                                                                   ('Quần kaki nam công sở', 300000, 85, 'Quần kaki nam công sở, thoải mái, lịch sự', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/December2024/quan-dai-kaki-ecc-pants-den_(2).jpg', 2),
                                                                                   ('Quần short nam thể thao', 150000, 110, 'Quần short nam thể thao, chất liệu thoáng mát', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/July2022/2Short_Graphene_copy.jpg', 2),
                                                                                   ('Quần jogger nam năng động', 250000, 95, 'Quần jogger nam, phong cách năng động', 'https://media3.coolmate.me/cdn-cgi/image/width=672,height=990,quality=85/uploads/December2024/24CM.MAWCS.JGZ996_-_Buluga_9.jpg', 2),
                                                                                   ('Quần tây nam cao cấp', 350000, 65, 'Quần tây nam cao cấp, phù hợp sự kiện', 'https://owen.cdn.vccloud.vn/media/catalog/product/cache/01755127bd64f5dde3182fd2f139143a/q/s/qs252423.png', 2);

-- Category: Giày (category_id = 3)
INSERT INTO products (name, price, stock, description, image_url, category_id) VALUES
                                                                                   ('Giày thể thao nam trắng', 500000, 50, 'Giày thể thao nam màu trắng, phong cách trẻ trung', 'https://ecdn6.globalso.com/upload/p/1048/image_product/2024-04/976119320006n9v9-2.jpg', 3),
                                                                                   ('Giày da nam công sở', 700000, 40, 'Giày da nam công sở, chất liệu da thật', 'https://www.bing.com/th?id=OIP.B4_rA2O_HeKgMVYBUraxGAHaHa&w=176&h=185&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2', 3),
                                                                                   ('Giày sneaker nam đen', 450000, 60, 'Giày sneaker nam màu đen, phong cách năng động', 'https://www.bing.com/th?id=OIP.vajAYdIKBpdO6IlRZTQ0AAHaHZ&w=150&h=149&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2', 3),
                                                                                   ('Giày lười nam cao cấp', 600000, 45, 'Giày lười nam, chất liệu cao cấp, thoải mái', 'https://www.bing.com/th?id=OIP.PYwTmWodc1ijcY8HcJUySAHaHa&w=176&h=185&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2', 3),
                                                                                   ('Giày chạy bộ nam', 550000, 55, 'Giày chạy bộ nam, hỗ trợ êm chân', 'https://th.bing.com/th?q=Gi%c3%a0y+Ch%e1%ba%a1y+B%e1%bb%99+Adidas&w=80&h=80&c=7&o=5&pid=1.7&mkt=en-US&cc=US&setlang=en&adlt=moderate&t=1', 3);

-- Category: Phụ Kiện (category_id = 4)
INSERT INTO products (name, price, stock, description, image_url, category_id) VALUES
                                                                                   ('Mũ lưỡi trai nam', 100000, 150, 'Mũ lưỡi trai nam, phong cách trẻ trung', 'https://www.bing.com/th?id=OIP.ZRZgmJDvcgwldV_Nzlx5swHaHa&w=150&h=150&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2', 4),
                                                                                   ('Thắt lưng da nam', 200000, 90, 'Thắt lưng da nam, chất liệu da thật', 'https://th.bing.com/th?id=OIP.U3dDO50YFnr1VZNWtZ_V9QHaHa&w=150&h=104&c=7&bgcl=4bd902&r=0&o=6&pid=13.1', 4),
                                                                                   ('Ví da nam cao cấp', 250000, 80, 'Ví da nam cao cấp, thiết kế sang trọng', 'https://th.bing.com/th?id=OIP.VhipPIP8Oqlcqksme0MMSAHaJQ&w=121&h=104&c=7&bgcl=fb282d&r=0&o=6&pid=13.1', 4),
                                                                                   ('Kính râm nam thời trang', 300000, 70, 'Kính râm nam, phong cách thời trang', 'https://th.bing.com/th?id=OIP.EJF7Td99l54XekCjUDHHcAHaI2&w=70&h=104&c=7&bgcl=a65d06&r=0&o=6&pid=13.1', 4),
                                                                                   ('Dây đồng hồ da', 150000, 100, 'Dây đồng hồ da, chất liệu bền bỉ', 'https://www.bing.com/th?id=OIP.jtpXeru_2cYb4Wo1vKgjPAHaHa&w=176&h=185&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2', 4);

select * from products;