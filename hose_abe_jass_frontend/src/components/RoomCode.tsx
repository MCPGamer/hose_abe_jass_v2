import React from 'react';
import './RoomCode.css';

type Props = {
    code: string;
}

export const RoomCode: React.FC<Props> = (props) => {
    return (
        <div className={'roomcode-container'}>
            <h2><p>Tell your Friends to<br/>Join using the code</p>
                <p>{props.code}</p></h2>
        </div>
    );
};
