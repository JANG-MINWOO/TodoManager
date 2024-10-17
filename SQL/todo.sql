# member TABLE
CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role ENUM('USER', 'ADMIN') DEFAULT 'USER',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

# todo TABLE
CREATE TABLE todo (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      member_id BIGINT,
                      title VARCHAR(255) NOT NULL,
                      description TEXT,
                      password VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

# comment TABLE
CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         todo_id BIGINT,
                         member_id BIGINT,
                         content TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (todo_id) REFERENCES todo(id) ON DELETE CASCADE,
                         FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

#중간테이블 member_todo
CREATE TABLE member_todo (
                             member_id BIGINT,
                             todo_id BIGINT,
                             PRIMARY KEY (member_id, todo_id),
                             FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
                             FOREIGN KEY (todo_id) REFERENCES todo(id) ON DELETE CASCADE
);