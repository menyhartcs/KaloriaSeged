import './App.css'
import HeaderComponent from "./component/HeaderComponent.jsx";
import FooterComponent from "./component/FooterComponent.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import UserComponent from "./component/UserComponent.jsx";
import ListUserComponent from "./component/ListUserComponent.jsx";
import FoodComponent from "./component/FoodComponent.jsx";
import ListFoodComponent from "./component/ListFoodComponent.jsx";
import ListUserLogsComponent from "./component/ListUserLogsComponent.jsx";
import UserFoodLogComponent from "./component/UserFoodLogComponent.jsx";
import UserLogInComponent from "./component/UserLogInComponent.jsx";
import UserSignUpComponent from "./component/UserSignUpComponent.jsx";
import UserLogOutComponent from "./component/UserLogOutComponent.jsx";
import UserProfileComponent from "./component/UserProfileComponent.jsx";
import CalorieCalculatorComponent from "./component/CalorieCalculatorComponent.jsx";
import ListExerciseComponent from "./component/ListExerciseComponent.jsx";
import ExerciseComponent from "./component/ExerciseComponent.jsx";
import UserExerciseLogComponent from "./component/UserExerciseLogComponent.jsx";

function App() {

    return (
        <>
            <BrowserRouter>
                <HeaderComponent/>
                    <Routes>
                        <Route path="/" element = { <ListUserLogsComponent/> }></Route>

                        <Route path="/UserLogIn" element = { <UserLogInComponent/> }></Route>
                        <Route path="/UserLogOut" element = { <UserLogOutComponent/> }></Route>
                        <Route path="/UserSignUp" element = { <UserSignUpComponent/> }></Route>
                        <Route path="/UserProfile" element = { <UserProfileComponent/> }></Route>
                        <Route path="/CalorieCalculator" element = { <CalorieCalculatorComponent/> }></Route>
                        <Route path="/Users" element = { <ListUserComponent/> }></Route>
                        <Route path="/add-user" element = { <UserComponent/> }></Route>
                        <Route path="/edit-user/:id" element = { <UserComponent/> }></Route>

                        <Route path="/Foods" element = { <ListFoodComponent/> }></Route>
                        <Route path="/add-food" element = { <FoodComponent/> }></Route>
                        <Route path="/edit-food/:id" element = { <FoodComponent/> }></Route>

                        <Route path="/UserLogs" element = { <ListUserLogsComponent/> }></Route>
                        <Route path="/userFoodLog/searchByUserIdAndDate" element = { <ListUserLogsComponent/> }></Route>
                        <Route path="/add-userFoodLog/:id" element = { <UserFoodLogComponent/> }></Route>
                        <Route path="/edit-userFoodLog/:id" element = { <UserFoodLogComponent/> }></Route>

                        <Route path="/Exercises" element = { <ListExerciseComponent/> }></Route>
                        <Route path="/add-exercise" element = { <ExerciseComponent/> }></Route>
                        <Route path="/edit-exercise/:id" element = { <ExerciseComponent/> }></Route>

                        <Route path="/userExerciseLog/searchByUserIdAndDate" element = { <ListUserLogsComponent/> }></Route>
                        <Route path="/add-userExerciseLog/:id" element = { <UserExerciseLogComponent/> }></Route>
                        <Route path="/edit-userExerciseLog/:id" element = { <UserExerciseLogComponent/> }></Route>
                    </Routes>
                <FooterComponent/>
            </BrowserRouter>
        </>
    )
}

export default App
