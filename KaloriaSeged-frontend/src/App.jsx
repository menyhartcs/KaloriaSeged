import './App.css'
import HeaderComponent from "./component/HeaderComponent.jsx";
import FooterComponent from "./component/FooterComponent.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import UserComponent from "./component/UserComponent.jsx";
import ListUserComponent from "./component/ListUserComponent.jsx";
import FoodComponent from "./component/FoodComponent.jsx";
import ListFoodComponent from "./component/ListFoodComponent.jsx";

function App() {

    return (
        <>
            <BrowserRouter>
                <HeaderComponent/>
                    <Routes>
                        <Route path="/" element = { <ListUserComponent/> }></Route>
                        <Route path="/Users" element = { <ListUserComponent/> }></Route>
                        <Route path="/add-user" element = { <UserComponent/> }></Route>
                        <Route path="/edit-user/:id" element = { <UserComponent/> }></Route>

                        <Route path="/Foods" element = { <ListFoodComponent/> }></Route>
                        <Route path="/add-food" element = { <FoodComponent/> }></Route>
                        <Route path="/edit-food/:id" element = { <FoodComponent/> }></Route>
                    </Routes>
                <FooterComponent/>
            </BrowserRouter>
        </>
    )
}

export default App
