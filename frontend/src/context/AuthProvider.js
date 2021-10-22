import {createContext, useState} from "react";
import {login} from "../service/devQuizApiService";
import {useHistory} from "react-router-dom";


export const AuthContext = createContext({})

export default function AuthProvider({children}){

    const[token, setToken] = useState()
    const history = useHistory()

    const auth = (username, password) => {
        login(username, password)
            .then(token => setToken(token))
            .then(() => history.push("/"))
    }
    return (
        <AuthContext.Provider value={{token, auth}}>{children}</AuthContext.Provider>
    )
}