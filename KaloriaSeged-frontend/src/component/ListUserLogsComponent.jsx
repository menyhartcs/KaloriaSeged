import React, {useEffect, useState} from 'react'
import {useNavigate} from "react-router-dom";
import {
    deleteUserFoodLog,
    listUserFoodLogsByDate,
    listUserFoodLogsByUserIdAndDate
} from "../service/UserFoodLogService.js";
import ProteinChart from "./charts/ProteinChart.jsx";
import CarbohydrateChart from "./charts/CarbohydrateChart.jsx";
import FatChart from "./charts/FatChart.jsx";
import CalorieChart from "./charts/CalorieChart.jsx";
import '../style/Charts.css';
import moment from 'moment';
import {isNullOrUndef} from "chart.js/helpers";
import {checkLoginStatus, getUserByEmail} from "../service/UserService.js";
import {analyze} from "../service/AnalyzerService.js";
import 'bootstrap-icons/font/bootstrap-icons.css';
import {deleteUserExerciseLog, listUserExerciseLogsByUserIdAndDate} from "../service/UserExerciseLogService.js";

const ListUserLogsComponent = () => {

    const [userFoodLogs, setUserFoodLogs] = useState([])
    const [userExerciseLogs, setUserExerciseLogs] = useState([])
    const [consumedNutrients, setConsumedNutrients] = useState({
        calorie: 0, protein: 0, carbohydrate: 0, fat: 0
    });
    const [doneExercises, setDoneExercises] = useState({
        calorie: 0
    });
    const [selectedDate, setSelectedDate] = useState(getCurrentDate);
    const [analysisResult, setAnalysisResult] = useState("");
    const [user, setUser] = useState([])
    const navigator = useNavigate();
    const [showCard, setShowCard] = useState(false);
    const currentEmail = localStorage.getItem("email");

    useEffect(() => {
        checkLoginStatus(currentEmail).then((response) => {
            if (!isNullOrUndef(response.data.role) && response.data.role === "ROLE_ADMIN") {
                navigator("/Users")
            }
        }).catch(() => {
            console.log("ISMERETLEN FELHASZNÁLÓ")
            navigator("/UserLogIn");
        })
    }, []);

    useEffect(() => {
        getUserByEmail(currentEmail).then((response) => {
            setUser(response.data);
        });
    }, []);

    const userId = user.id;
    const date = getCurrentDate();

    useEffect(() => {
        if (isNullOrUndef(userId)) {
            getUserFoodLogsByDate(selectedDate);
        } else {
            getUserFoodLogsByUserIdAndDate(userId, date);
            getUserExerciseLogsByUserIdAndDate(userId, date);
        }

    }, [userId, selectedDate]);

    useEffect(() => {
        if (userFoodLogs.length > 0) {
            const summedNutrients = userFoodLogs.reduce((dictionary, log) => {
                const food = log.food || {};
                return {
                    calorie: dictionary.calorie + (Math.round(food.calorie * log.amount / 100) || 0),
                    protein: dictionary.protein + (Math.round(food.protein * log.amount / 100) || 0),
                    carbohydrate: dictionary.carbohydrate + (Math.round(food.carbohydrate * log.amount / 100) || 0),
                    fat: dictionary.fat + (Math.round(food.fat * log.amount / 100) || 0)
                };
            }, { calorie: 0, protein: 0, carbohydrate: 0, fat: 0 });

            setConsumedNutrients(summedNutrients);
        }
    }, [userFoodLogs]);

    useEffect(() => {
        if (userExerciseLogs.length > 0) {
            const summedExercises = userExerciseLogs.reduce((dictionary, log) => {
                const exercise = log.exercise || {};
                return {
                    calorie: dictionary.calorie + (Math.round(exercise.calorie * log.duration) || 0)
                };
            }, { calorie: 0 });

            setDoneExercises(summedExercises);
        }
    }, [userExerciseLogs]);

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    function getUserFoodLogsByDate(date) {
        listUserFoodLogsByDate(date).then((response) => {
            setUserFoodLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getUserExerciseLogsByDate(date) {
        listUserExerciseLogsByDate(date).then((response) => {
            setUserFoodLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getUserFoodLogsByUserIdAndDate(userId, date) {
        listUserFoodLogsByUserIdAndDate(userId, date).then((response) => {
            setUserFoodLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getUserExerciseLogsByUserIdAndDate(userId, date) {
        listUserExerciseLogsByUserIdAndDate(userId, date).then((response) => {
            setUserExerciseLogs(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewUserFoodLog() {
        navigator("/Foods")
    }

    function updateUserFoodLog(id) {
        navigator(`/edit-userFoodLog/${id}`)
    }

    function addNewUserExerciseLog() {
        navigator("/Exercises")
    }

    function updateUserExerciseLog(id) {
        navigator(`/edit-userExerciseLog/${id}`)
    }

    function analyzeUserFoodLog(prompt) {
        setShowCard(!showCard)
        if (showCard === true) {
            setAnalysisResult("");
        }
        setAnalysisResult("Elemzés folyamatban....");
        analyze(prompt).then(response => {
            setAnalysisResult(response.data);
        })
        .catch(error => {
            console.error(error);
            setAnalysisResult("Error analyzing data");
        });
    }

    function getSimpleFoodList() {
        return userFoodLogs.map(userFoodLog =>
            `Étel neve: ${userFoodLog.food.name},
             Étel energia tartalom (kcal): ${userFoodLog.food.calorie},
             Étel fehérje tartalom (g): ${userFoodLog.food.protein},
             Étel szénhidrát tartalom (g): ${userFoodLog.food.carbohydrate},
             Étel zsír tartalom (g): ${userFoodLog.food.fat}
            `);
    }


    function removeUserFoodLog(id) {
        console.log(id);

        deleteUserFoodLog(id).then(() => {
            if (isNullOrUndef(userId)) {
                getUserFoodLogsByDate(selectedDate);
            } else {
                getUserFoodLogsByUserIdAndDate(userId, date);
            }
        }).catch(error => {
            console.error(error);
        })
    }

    function removeUserExerciseLog(id) {
        console.log(id);

        deleteUserExerciseLog(id).then(() => {
            if (isNullOrUndef(userId)) {
                getUserExerciseLogsByDate(selectedDate);
            } else {
                getUserExerciseLogsByUserIdAndDate(userId, date);
            }
        }).catch(error => {
            console.error(error);
        })
    }

    const handleChangeDate = (event) => {
        const newDate = event.target.value;
        setSelectedDate(newDate);
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            if (isNullOrUndef(userId)) {
                getUserFoodLogsByDate(selectedDate);
            } else {
                getUserFoodLogsByUserIdAndDate(userId, selectedDate);
                getUserExerciseLogsByUserIdAndDate(userId, selectedDate);
            }
        }
    };

    return (
        <div className="container main-content m-6">
            <h2 className="text-center">Étkezési napló</h2>
            <div className="row">
                <div className="col-md-8">
                    <div className="card p-3">
                        <label htmlFor="dateInput" className="form-label">Jelenlegi dátum:</label>
                        <input
                            type="date"
                            id="dateInput"
                            className="form-control mb-1"
                            value={selectedDate}
                            onChange={handleChangeDate}
                            onKeyDown={handleKeyDown}
                        />
                        <div className="row">
                            <div className="col-md-7">
                                <div className="card p-3">
                                    <button className="btn btn-dark mb-2" onClick={addNewUserFoodLog}>Étel fogyasztás
                                    </button>
                                    <table className="table table-striped table-bordered mt-3">
                                        <tbody>
                                        {
                                            userFoodLogs.map(userFoodLog =>
                                                <tr key={userFoodLog.id}>
                                                    <td>
                                                        <h4><b>{userFoodLog.food.name}</b></h4>
                                                    </td>
                                                    <td className="text-nowrap">
                                                        <b className="nutritionText energy">Energia:</b> {Math.round(userFoodLog.food.calorie * userFoodLog.amount / 100)} kcal <br/>
                                                        <b className="nutritionText protein">Fehérje:</b> {Math.round(userFoodLog.food.protein * userFoodLog.amount / 100)} g <br/>
                                                        <b className="nutritionText carbohydrate">Szénhidrát:</b> {Math.round(userFoodLog.food.carbohydrate * userFoodLog.amount / 100)} g <br/>
                                                        <b className="nutritionText fat">Zsír:</b> {Math.round(userFoodLog.food.fat * userFoodLog.amount / 100)} g
                                                    </td>
                                                    <td>
                                                        <button className="btn btn-warning m-1"
                                                                title="Szerkeszt"
                                                                onClick={() => updateUserFoodLog(userFoodLog.id)}><i
                                                            className="bi bi-pencil"></i>
                                                        </button>
                                                        <br/>
                                                        <button className="btn btn-danger m-1"
                                                                title="Töröl"
                                                                onClick={() => removeUserFoodLog(userFoodLog.id)}><i
                                                            className="bi bi-trash"></i>
                                                        </button>
                                                        <br/>
                                                        <button className="btn btn-info m-1"
                                                                title="Információ"
                                                                onClick={() => analyzeUserFoodLog(
                                                                    `Röviden elemezd az ételt:
                                                             Energia: ${Math.round(userFoodLog.food.calorie * userFoodLog.amount / 100)}
                                                             Zsír: ${Math.round(userFoodLog.food.fat * userFoodLog.amount / 100)}
                                                             Szénhidrát: ${Math.round(userFoodLog.food.carbohydrate * userFoodLog.amount / 100)}
                                                             Fehérje: ${Math.round(userFoodLog.food.protein * userFoodLog.amount / 100)}
                                                             és adj tanácsot, mikor lenne érdemes fogyasztani, nagyon röviden`
                                                                )}><i className="bi bi-info-circle"></i>
                                                        </button>
                                                    </td>
                                                </tr>)
                                        }
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div className="col-md-5">
                                <div className="card p-3">
                                    <button className="btn btn-dark mb-2" onClick={addNewUserExerciseLog}>Tevékenység naplózása
                                    </button>
                                    <table className="table table-striped table-bordered mt-3">
                                        <tbody>
                                        {
                                            userExerciseLogs.map(userExerciseLog =>
                                                <tr key={userExerciseLog.id}>
                                                    <td>
                                                        <div className="form-group m-1">
                                                            <h4><b>{userExerciseLog.exercise.name}</b></h4>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <b className="nutritionText energy">Energia:</b>
                                                        <br/>
                                                        {Math.round(userExerciseLog.exercise.calorie * userExerciseLog.duration)} kcal

                                                    </td>
                                                    <td>
                                                        <button className="btn btn-warning m-1"
                                                                onClick={() => updateUserExerciseLog(userExerciseLog.id)}>
                                                            <i className="bi bi-pencil"></i>
                                                        </button>
                                                        <button className="btn btn-danger m-1"
                                                                onClick={() => removeUserExerciseLog(userExerciseLog.id)}>
                                                            <i className="bi bi-trash"></i>
                                                        </button>
                                                    </td>
                                                </tr>)
                                        }
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div className="col-md-4">
                    <div className="card p-3">
                        <div className="charts-container">
                            <ProteinChart user={user} consumedProtein={consumedNutrients.protein}/>
                            <CarbohydrateChart user={user} consumedCarbohydrate={consumedNutrients.carbohydrate}/>
                            <FatChart user={user} consumedFat={consumedNutrients.fat}/>
                            <CalorieChart maxCalorie={user.calorie + doneExercises.calorie} consumedCalorie={consumedNutrients.calorie}/>
                        </div>
                        <button
                            className="btn btn-info mt-3"
                            onClick={() => analyzeUserFoodLog(
                                `Ez egy étkezési napló: ${getSimpleFoodList()} nagyon röviden elemezd a 
                                napi beállított személyes cél és bevitel függvényében:
                                Napi kitűzött energia bevitel: ${user.calorie}
                                Napi kitűzött fehérje bevitel: ${user.protein}
                                Napi kitűzött szénhidrát bevitel: ${user.carbohydrate}
                                Napi kitűzött zsír bevitel: ${user.fat}
                                Mai bevitt energia (kcal): ${consumedNutrients.calorie}
                                Mai bevitt fehérje (g): ${consumedNutrients.protein}
                                Mai bevitt szénhidrát (g): ${consumedNutrients.carbohydrate}
                                Mai bevitt zsír (g): ${consumedNutrients.fat}
                                és személyes adatok alapján:
                                Súly: ${user.weight}
                                Magasság: ${user.height}
                                Kor: ${user.age}
                                és adj tanácsot, mire lenne érdemes odafigyelni, nagyon röviden, így tagold a választ:
                                -Nagyon röviden a napi makrótápanyag bevitelről és arányokról
                                -Nagyon röviden a tanács`
                            )}>
                            {showCard ? "Elrejtés" : "Elemzés"}
                        </button>
                        {showCard && (
                            <div>
                                <h5>További információ</h5>
                                <p>{analysisResult}</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ListUserLogsComponent
