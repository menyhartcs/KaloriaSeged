import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

const REST_API_BASE_URL = '/userFoodLog';

export const listUserFoodLogs = () => axiosClient.get(REST_API_BASE_URL);

export const listUserFoodLogsByDate = (date) => axiosClient.get(REST_API_BASE_URL + "/searchByDate?date=" + date);

export const listUserFoodLogsByUserIdAndDate = (userId, date) =>
    axiosClient.get(REST_API_BASE_URL + "/searchByUserIdAndDate?userId=" + userId + "&date=" + date);

export const createUserFoodLog = (userFoodLog) => axiosClient.post(REST_API_BASE_URL, userFoodLog);

export const getUserFoodLog = (userFoodLogId) => axiosClient.get(REST_API_BASE_URL + "/" + userFoodLogId);

export const updateUserFoodLog = (userFoodLogId, userFoodLog) =>
    axiosClient.put(REST_API_BASE_URL + "/" + userFoodLogId, userFoodLog);

export const deleteUserFoodLog = (userFoodLogId) => axiosClient.delete(REST_API_BASE_URL + "/" + userFoodLogId);