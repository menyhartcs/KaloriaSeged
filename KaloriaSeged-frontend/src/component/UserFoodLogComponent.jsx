import React, {useEffect, useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import {createUserFoodLog, getUserFoodLog, updateUserFoodLog} from "../service/UserFoodLogService.js";
import moment from "moment/moment.js";
import {getUserByEmail} from "../service/UserService.js";
import {getFoodById} from "../service/FoodService.js";
import {isNullOrUndef} from "chart.js/helpers";

const UserFoodLogComponent = () => {

    const [User, setUser] = useState({})
    const [Food, setFood] = useState({})
    const [date, setDate] = useState(getCurrentDate)
    const [amount, setAmount] = useState(100)
    const location = useLocation();
    const currentUrl = location.pathname.split('/')[1]
    const navigator = useNavigate();
    const {id} = useParams();
    const amountMultiplier =  amount / 100;

    useEffect(() => {
        if (isNullOrUndef(localStorage.getItem("email")) && isNullOrUndef(localStorage.getItem("token"))) {
            navigator("/UserLogIn");
        }
    }, []);

    const [errors, setErrors] = useState({
        User: "",
        Food: "",
        date: "",
        amount: ""
    })

    useEffect(() =>{
        if (currentUrl === "edit-userFoodLog") {
            getUserFoodLog(id).then((response) => {
                const userData = response.data.user || {};
                const foodData = response.data.food || {};
                const logDate = response.data.date || getCurrentDate();
                const logAmount = response.data.amount || {};

                setUser(userData);
                setFood(foodData);
                setDate(logDate);
                setAmount(logAmount);
            }).catch(error => {
                console.error(error)
            })
        }
    }, [id])

    let email = localStorage.getItem("email")
    useEffect(() =>{
        if (currentUrl === "add-userFoodLog") {
            getFoodById(id).then((response) => {
                setFood(response.data)
            }).catch(error => {
                console.error(error)
            })
            getUserByEmail(email).then((response) => {
                setUser(response.data);
            }).catch(error => {
                console.error(error)
            })
            console.log(User)
            console.log(Food)
        }
    }, [])

    function getCurrentDate() {
        return moment().format('YYYY-MM-DD');
    }

    const handleChangeDate = (event) => {
        const newDate = event.target.value;
        setDate(newDate);
    };

    function saveOrUpdateUserFoodLog(e) {
        e.preventDefault();

        if (validateForm()) {

            const userFoodLog = {user: User, food: Food, date, amount}
            console.log(userFoodLog)

            if (currentUrl === "edit-userFoodLog") {
                updateUserFoodLog(id, userFoodLog).then((response) => {
                    console.log(response.data);
                    navigator("/UserLogs");
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createUserFoodLog(userFoodLog).then((response) => {
                    console.log(response.data)
                    navigator("/UserLogs")
                }).catch(error => {
                    console.error(error);
                })
            }

        }

    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if (User.id) {
            errorsCopy.User = "";
        } else {
            errorsCopy.User = "User id is required!";
            valid = false;
        }

        if (Food.id) {
            errorsCopy.Food = "";
        } else {
            errorsCopy.Food = "Food id is required!";
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    return (
        <div className="container main-content">
            <br/><br/>
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">Naplóbejegyzés</h2>
                    <div className="card-body">
                        <form>
                            <div className="form-group mb-2">
                                <h4><b>{Food.name}</b></h4>
                            </div>

                            <div className="form-group mb-2">
                                <ul>
                                    <li><b className="nutritionText energy">Energia:</b> {Math.round(Food.calorie * amountMultiplier)} kcal</li>
                                    <li><b className="nutritionText protein">Fehérje:</b> {Math.round(Food.protein * amountMultiplier)} g</li>
                                    <li><b className="nutritionText carbohydrate">Szénhidrát:</b> {Math.round(Food.carbohydrate * amountMultiplier)} g</li>
                                    <li><b className="nutritionText fat">Zsír:</b> {Math.round(Food.fat * amountMultiplier)} g</li>
                                </ul>
                            </div>

                            <div className="form-group col-md-4 mb-2">
                                <label className="form-label">Mennyiség (g):</label>
                                <input type="number"
                                       name="amount"
                                       value={amount}
                                       className={`form-control ${errors.amount ? "is-invalid" : ""}`}
                                       onChange={(e) => setAmount(e.target.value)}
                                />
                                {errors.amount && <div className="invalid-feedback">{errors.amount}</div>}
                            </div>

                            <div className="mb-3">
                                <label htmlFor="dateInput" className="form-label">Válaszd ki melyik nap fogyasztottad:</label>
                                <input
                                    type="date"
                                    id="dateInput"
                                    className="form-control"
                                    value={date || getCurrentDate()}
                                    onChange={handleChangeDate}
                                />
                            </div>

                            <button className="btn btn-success" onClick={saveOrUpdateUserFoodLog}>Mentés</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserFoodLogComponent