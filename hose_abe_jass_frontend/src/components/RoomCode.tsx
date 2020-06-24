import React from 'react';
import './RoomCode.css';

type Props = {
    code: string;
}

export const RoomCode: React.FC<Props> = (props) => {
    return (
        <div className={'roomcode-container'}>
            <h2><p>Gib den folgenden Raumcode<br/> deinen Kollegen um beizutreten</p>
                <p>{props.code}</p></h2>
        </div>
    );
};
