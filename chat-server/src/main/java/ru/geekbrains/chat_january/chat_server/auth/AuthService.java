package ru.geekbrains.chat_january.chat_server.auth;

import ru.geekbrains.chat_january.chat_server.entity.User;

public interface AuthService {
    void start();
    void stop();

    String authorizeUserByLoginAndPassword (String login, String password);
    User createNewUser(String login, String password, String nickName);
    void deleteUser(String login, String password);
    String changeNickname(String newNickname);
    void changePassword(String login, String oldPass, String newPass);
    void resetPassword(String login, String newPass, String secretWord);
}
