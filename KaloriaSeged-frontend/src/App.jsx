import {useState} from 'react'
import './App.css'
import ListUserComponent from "./component/ListUserComponent.jsx";
import HeaderComponent from "./component/HeaderComponent.jsx";
import FooterComponent from "./component/FooterComponent.jsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";

function App() {

    return (
        <>
            <BrowserRouter>
                <HeaderComponent/>
                    <Routes>
                        <Route path="/" element = { <ListUserComponent/> }></Route>
                        <Route path="/Users" element = { <ListUserComponent/> }></Route>
                    </Routes>
                <FooterComponent/>
            </BrowserRouter>
        </>
    )
}

export default App
