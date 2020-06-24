import React from 'react';
import './PlayerTable.css';
import {Room} from '../models/room';

type Props = {
    room: Room;
    player: string;
    revealCards?: boolean;
}

export const PlayerTable: React.FC<Props> = (props) => {
    return (
        <div className={'player-container'}>
            <table>
                <tbody>
                {props.room.players.map((p) => (
                    p !== null ?
                        <tr key={p.name}>
                            <td className={props.room.players[props.room.playerturn].name === p.name ? 'bold' : ''}>
                                {p.name === props.player ? p.name + ' (you)' : p.name}
                                {props.revealCards ? p.cards.map((c) => (
                                    <img className={'small-card'} alt={'Card Image'}
                                           src={`${process.env.PUBLIC_URL}/img/jasskarten/${c.cardColor}${c.cardValue}Klein.gif'}`}/>
                                )) : ''}
                            </td>
                        </tr>
                        : ''
                ))}
                </tbody>
            </table>
        </div>
    );
};
