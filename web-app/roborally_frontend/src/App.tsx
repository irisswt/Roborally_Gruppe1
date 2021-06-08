import React from 'react';
import BoardComponent from "./components/BoardComponent";
import GamesComponent from "./components/GamesComponent";
import GameContextProvider from "./context/GameContextProvider";
import { ToastProvider, useToasts } from 'react-toast-notifications';


function App() {

    return (
        <div className="App">
            <header className="App-header">
            </header>
            {/*Context provider component below makes sure the context is accessible in any children components*/}
            <ToastProvider>
                <GameContextProvider>
                    <GamesComponent />
                    <BoardComponent />
                </GameContextProvider>
            </ToastProvider>
        </div>
    );
}

export default App;
