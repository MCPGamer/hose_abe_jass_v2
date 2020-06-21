import React from 'react';
import {RoomManager} from '../components/RoomManager';
import './MainMenu.css'

export const MainMenuContainer: React.FC = () => {
    return (
        <div className="center-form">
        <RoomManager/>
        </div>
    )
}
