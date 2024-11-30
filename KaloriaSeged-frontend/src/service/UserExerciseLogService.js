import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
});

axiosClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

const REST_API_BASE_URL = '/userExerciseLog';

export const listUserExerciseLogs = () => axiosClient.get(REST_API_BASE_URL);

export const listUserExerciseLogsByDate = (date) => axiosClient.get(REST_API_BASE_URL + "/searchByDate?date=" + date);

export const listUserExerciseLogsByUserIdAndDate = (userId, date) =>
    axiosClient.get(REST_API_BASE_URL + "/searchByUserIdAndDate?userId=" + userId + "&date=" + date);

export const createUserExerciseLog = (userExerciseLog) => axiosClient.post(REST_API_BASE_URL, userExerciseLog);

export const getUserExerciseLog = (userExerciseLogId) => axiosClient.get(REST_API_BASE_URL + "/" + userExerciseLogId);

export const updateUserExerciseLog = (userExerciseLogId, userExerciseLog) =>
    axiosClient.put(REST_API_BASE_URL + "/" + userExerciseLogId, userExerciseLog);

export const deleteUserExerciseLog = (userExerciseLogId) => axiosClient.delete(REST_API_BASE_URL + "/" + userExerciseLogId);