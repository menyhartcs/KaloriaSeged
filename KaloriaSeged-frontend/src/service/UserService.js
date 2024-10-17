import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/user';
const REGISTRATION_URL = 'http://localhost:8080/registration/register';
const LOGIN_URL = 'http://localhost:8080/login';

export const listUsers = () => axios.get(REST_API_BASE_URL);
export const createUser = (user) => axios.post(REGISTRATION_URL, user);
export const loginUser = (user) => axios.post(LOGIN_URL, user);
export const getUserById = (userId) => axios.get(REST_API_BASE_URL + "/id/" + userId);
export const getUserByEmail = (email) => axios.get(REST_API_BASE_URL + "/email/" + email);
export const updateUser = (userId, user) => axios.put(REST_API_BASE_URL + "/" + userId, user);
export const deleteUser = (userId) => axios.delete(REST_API_BASE_URL + "/" + userId);