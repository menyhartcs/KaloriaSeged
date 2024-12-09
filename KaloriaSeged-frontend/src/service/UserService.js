import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

const REST_API_BASE_URL = '/user';
const REGISTRATION_URL = '/registration/register';
const LOGIN_URL = '/api/login';

export const listUsers = () => axiosClient.get(REST_API_BASE_URL)
export const createUser = (user) => axiosClient.post(REGISTRATION_URL, user);
export const loginUser = (user) => axiosClient.post(LOGIN_URL, user);
export const checkLoginStatus = (email) => axiosClient.get(REST_API_BASE_URL + "/email/" + email);
export const logOutUser = () => axiosClient.post("/logout");
export const getUserById = (userId) => axiosClient.get(REST_API_BASE_URL + "/id/" + userId);
export const getUserByEmail = (email) => axiosClient.get(REST_API_BASE_URL + "/email/" + email);
export const updateUser = (userId, user) => axiosClient.put(REST_API_BASE_URL + "/" + userId, user);
export const deleteUser = (userId) => axiosClient.delete(REST_API_BASE_URL + "/" + userId);