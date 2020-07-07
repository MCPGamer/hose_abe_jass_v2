import React from 'react';
import './PlayerTable.css';
import {Room} from '../models/room';
import {Button} from "@material-ui/core";

type Props = {
    room: Room;
    player: string;
    isHost: boolean;
    revealCards?: boolean;
    onKick: (name:string) => void;
}

export const PlayerTable: React.FC<Props> = (props) => {
    const kick = (name: string) => {
        props.onKick(name);
    };

    return (
        <div className={'player-container'}>
            <table className={'player-table'}>
                <tbody>
                {props.room.players.map((p) => (
                    p !== null ?
                        <tr key={p.name}>
                            <td className={'circle'}>
                                {props.room.players[props.room.playerTurn].name === p.name && !props.revealCards ? '●' : ''}
                            </td>
                            <td className={props.room.players[props.room.playerTurn].name === p.name && !props.revealCards ? 'bold' : ''}>
                                {p.name === props.player ? p.name + ' (du)' : p.name}
                            </td>
                            <td>
                                {p.hasBonusLife ? p.life + ' (schwimmt)' : p.life}
                            </td>
                            <td>
                                {p.finalTurnPlayed ? ' Klopft' : ''}
                            </td>
                            {props.isHost ? <td>
                                <Button variant={'outlined'} onClick={() => kick(p.name)}>Kick</Button>
                            </td> : ''}
                            <td>
                                {props.revealCards ? p.cards.map((c) => (
                                    <img key={c.cardColor + c.cardValue} className={'small-card'} alt={'Karte'}
                                         src={`${process.env.PUBLIC_URL}/img/jasskarten/${c.cardColor}${c.cardValue}Klein.gif`}/>
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
