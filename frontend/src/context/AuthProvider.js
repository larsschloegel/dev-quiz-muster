import {createContext, useState} from "react";
import {login} from "../service/devQuizApiService";
import {useHistory} from "react-router-dom";
import axios from "axios";


export const AuthContext = createContext({})

export default function AuthProvider({children}){

    const[token, setToken] = useState()
    const history = useHistory()

    const auth = (username, password) => {
        login(username, password)
            .then(token => setToken(token))
            .then(() => history.push("/"))
    }

    const loginWithGithub = code => {
        axios
            .post("/auth/github/login", {code})
            .then(res => res.data)
            .then(setToken)
            .then(()=> history.push("/"))
            .catch(error => console.error(error.message))
    }

    return (
        <AuthContext.Provider value={{token, auth, loginWithGithub}}>{children}</AuthContext.Provider>
    )
}