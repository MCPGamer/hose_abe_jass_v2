import React, {useEffect, useState} from 'react';
import './GameScreen.css';
import {Room} from '../models/room';
import {RoomCode} from '../components/RoomCode';
import {PlayerTable} from '../components/PlayerTable';
import {CardHand} from '../components/CardHand';
import {Card} from '../models/card';
import {Player} from '../models/player';
import {Button} from '@material-ui/core';

type Props = {
    room: Room,
    player: string
    backendUrl?: String;
    handleSetRoom: (room:Room) => void;
};

export const GameScreenContainer: React.FC<Props> = (props) => {
    const [enabled, setEnabled] = useState<boolean>(props.room.players[props.room.playerturn].name === props.player);
    const [player, setPlayer] = useState<Player>({name:'', cards:[]});

    useEffect(() => {
        setEnabled(props.room.players[props.room.playerturn].name === props.player);
        const player = props.room.players.find((p) => p.name === props.player);
        if(player !== undefined) {
            setPlayer(player);
        }
    }, [props.room, props.player]);

    if (!props.backendUrl) {
        props.backendUrl = 'localhost:8080';
    }

    const handleSwap = (card:Card) => {
        //TODO: this
    };

    const startGame = () => {
        fetch('http://' + props.backendUrl + '/room/start/' + props.room.roomCode, {method: 'POST'}).then(response => response.json())
            .then(room => props.handleSetRoom(room))
    };

    return (
        <div className="center-form">
            <PlayerTable room={props.room} player={props.player}/>
            {props.room.table.filter((c) => c !== null).length !== 0 ? (
                <div className={'center-text'}>
                    <div className={'table'}>
                    <p>Hose Abe</p>
                    <CardHand cards={props.room.table} handleSwap={handleSwap} enabled={enabled}/>
                        <p>TODO: Add Button "Klopfen" and Button "Alle Drei Nehmen</p>
                    </div>
                    <div className={'hand, center-element'}>
                        <CardHand cards={player.cards} handleSwap={handleSwap} enabled={enabled}/>
                    </div>
                </div>
                ) : props.room.host.name !== props.player ? 'Waiting on Host to start the game' :
                <div className={'center-text'}>
                    <p>Start the game as soon as everybody joined</p>
                    {props.room.players.filter(player => player != null).length > 1 ?
                        <Button variant="outlined" className={'center-element'} onClick={startGame}>Start Game</Button> : ''}
                </div>
				    }
            <RoomCode code={props.room.roomCode}/>
        </div>
    )
};
