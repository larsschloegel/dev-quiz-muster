import Header from './components/Header'
import './App.css'
import {Route, Switch} from 'react-router-dom'
import Homepage from './pages/Homepage'
import AddQuestion from './pages/Add-Question'
import useQuestions from './hooks/useQuestions'
import Play from "./pages/Play";
import {useContext, useEffect, useState} from "react";
import {getQuestion} from "./service/devQuizApiService";
import LoginPage from "./pages/Login-Page";
import  {AuthContext} from "./context/AuthProvider";
import PrivateRoute from "./routing/PrivateRoute";
import GitHubRedirectPage from "./pages/GitHubRedirectPage";

function App() {

    const [playQuestion, setPlayQuestion] = useState()
    const {token} = useContext(AuthContext)
    const {questions, saveQuestion} = useQuestions()

    const getNextQuestion = () => {
        getQuestion(token).then(result => {
            setPlayQuestion(result)
        })
    }

    useEffect(() => {
        getNextQuestion(token);
    }, [token]);

    return (
        <div className="App">

            <Header/>
                <Switch>
                    <Route exact path="/oauth/github/redirect">
                        <GitHubRedirectPage/>
                    </Route>
                    <PrivateRoute exact path="/">
                        <Homepage questions={questions}/>
                    </PrivateRoute>
                    <Route exact path="/login">
                        <LoginPage/>
                    </Route>
                    <PrivateRoute exact path="/add-question">
                        <AddQuestion saveQuestion={saveQuestion}/>
                    </PrivateRoute>
                    <PrivateRoute path="/play">
                        {playQuestion && <Play question={playQuestion} playNext={getNextQuestion} token={token}/>}
                    </PrivateRoute>
                </Switch>

        </div>
    )
}

export default App
