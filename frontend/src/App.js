import Header from './components/Header'
import './App.css'
import {Route, Switch, useHistory} from 'react-router-dom'
import Homepage from './pages/Homepage'
import AddQuestion from './pages/Add-Question'
import useQuestions from './hooks/useQuestions'
import Play from "./pages/Play";
import {useEffect, useState} from "react";
import {getQuestion, login} from "./service/devQuizApiService";
import LoginPage from "./pages/Login-Page";

function App() {

    const [playQuestion, setPlayQuestion] = useState()
    const [token, setToken] = useState()
    const {questions, saveQuestion} = useQuestions(token)
    const history = useHistory()

    const getNextQuestion = (token) => {
        getQuestion(token).then(result => {
            setPlayQuestion(result)
        })
    }

    const auth = (username, password) => {
        login(username, password)
            .then(token => setToken(token))
            .then(() => history.push("/"))
    }

    useEffect(() => {
        getNextQuestion(token);
    }, [token]);

    return (
        <div className="App">
            <Header/>
            <Switch>
                <Route exact path="/">
                    <Homepage questions={questions}/>
                </Route>
                <Route exact path="/login">
                    <LoginPage login={auth}/>
                </Route>
                <Route exact path="/add-question">
                    <AddQuestion saveQuestion={saveQuestion}/>
                </Route>
                <Route path="/play">
                    {playQuestion && <Play question={playQuestion} playNext={getNextQuestion} token={token}/>}
                </Route>
            </Switch>
        </div>
    )
}

export default App
