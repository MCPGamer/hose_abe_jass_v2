import React, {useEffect, useState} from 'react';
import './GameScreen.css';
import {defaultRoom, Room} from '../models/room';
import {RoomCode} from '../components/RoomCode';
import {PlayerTable} from '../components/PlayerTable';
import {CardHand} from '../components/CardHand';
import {Card, defaultCard} from '../models/card';
import {Player} from '../models/player';
import {Button} from '@material-ui/core';

type Props = {
  room: Room,
  player: string
  backendUrl?: String;
  handleSetRoom: (room: Room) => void;
};

export const GameScreenContainer: React.FC<Props> = (props) => {
  const [enabled, setEnabled] = useState<boolean>(false);
  const [player, setPlayer] = useState<Player>({name: '', cards: [], finalTurnPlayed: false, life:3, hasBonusLife:false});
  const [swapCard, setSwapCard] = useState<Card>(defaultCard);

  useEffect(() => {
    setEnabled(props.room.players[props.room.playerTurn].name === props.player);
    const player = props.room.players.find((p) => p.name === props.player);
    if (player !== undefined) {
      setPlayer(player);
    }
  }, [props.room, props.player]);

  if (!props.backendUrl) {
    props.backendUrl = 'localhost:8080';
  }

  const handleSwapFromTable = (card: Card) => {
    handleSwap(card, props.room.table, false);
  };

  const handleSwapFromHand = (card: Card) => {
    handleSwap(card, player.cards, true);
  };

  const handleSwap = (card: Card, hand: Card[], callFromHand: boolean) => {
    if (swapCard === defaultCard) {
      setSwapCard(card);
    } else {
      if (swapCard === card) {
        setSwapCard(defaultCard);
      } else if (hand.filter((c) => (c === swapCard)).length !== 0) {
        setSwapCard(card);
      } else {
        let playerCardIndex: number = 0;
        let tableCardIndex: number = 0;
        if (callFromHand) {
          playerCardIndex = player.cards.indexOf(card);
          tableCardIndex = props.room.table.indexOf(swapCard);
        } else {
          playerCardIndex = player.cards.indexOf(swapCard);
          tableCardIndex = props.room.table.indexOf(card);
        }

        fetch(`http://${props.backendUrl}/room/swapSingle/${props.room.roomCode}/${props.player}/${playerCardIndex}/${tableCardIndex}`, {method:'POST'})
            .then(response => response.json())
            .then(data => {
              setSwapCard(defaultCard);
              props.handleSetRoom(data);
            });
      }
    }
  };

  const swapAll = () => {
    fetch(`http://${props.backendUrl}/room/swapAll/${props.room.roomCode}/${props.player}`, {method:'POST'})
        .then(response => response.json())
        .then(data => {
          setSwapCard(defaultCard);
          props.handleSetRoom(data);
        });
  };

  const swapNone = () => {
    fetch(`http://${props.backendUrl}/room/swapNone/${props.room.roomCode}/${props.player}`, {method:'POST'})
        .then(response => response.json())
        .then(data => {
          setSwapCard(defaultCard);
          props.handleSetRoom(data);
        });
  };

  const startGame = () => {
    fetch('http://' + props.backendUrl + '/room/start/' + props.room.roomCode, {method: 'POST'}).then(response => response.json())
        .then(room => props.handleSetRoom(room));
  };

  const backToMainMenu = () => {
    fetch(`http://${props.backendUrl}/room/close/${props.room.roomCode}`).then();
    props.handleSetRoom(defaultRoom);
  };

  const nextRound = () => {
    fetch(`http://${props.backendUrl}/room/round/${props.room.roomCode}`, {method: 'POST'})
        .then(response => response.json())
        .then(room => props.handleSetRoom(room));
  };

  const kick = (name:string) => {

  }

  return (
      <div className="center-form">
        <PlayerTable room={props.room} player={props.player} revealCards={props.room.roundOver} isHost={props.room.host.name === props.player} onKick={kick}/>
        {props.room.gameOver ? (
            <div className={'center-text'}>
              <p>Die Spiel ist fertig, Glückwunsch {props.room.players[0].name}</p>
              <Button variant={'outlined'} onClick={backToMainMenu}>Zurück zum Hauptmenü</Button>
            </div>
        ) : props.room.roundOver ? (
            <div className={'center-text'}>
              <p>Die Runde ist fertig</p>
              <Button variant={'outlined'} disabled={!enabled} onClick={nextRound}>Nächste Runde</Button>
            </div>
        ) : props.room.table.filter((c) => c !== null).length !== 0 ? (
            <div className={'center-text'}>
              <div className={'table'}>
                <p>Hose Abe</p>
                <CardHand cards={props.room.table} handleSwap={handleSwapFromTable} enabled={enabled}
                          selectedCard={swapCard}/>
                          <div className={'inline'}>
                              <Button className={'center-element'} variant="outlined" onClick={swapAll} disabled={!enabled}>Alle 3 Nehmen</Button>
                              <Button className={'center-element'} variant="outlined" onClick={swapNone} disabled={!enabled}>Klopfen</Button>
                          </div>
              </div>
              <div className={'hand center-element'}>
                <CardHand cards={player.cards} handleSwap={handleSwapFromHand} enabled={enabled}
                          selectedCard={swapCard}/>
              </div>
            </div>
        ) : props.room.host.name !== props.player ? 'Warten bis der Host das Spiel startet' :
            <div className={'center-text'}>
              <p>Start das Spiel sobald alle dem Raum beigetreten sind</p>
              {props.room.players.filter(player => player !== null).length > 1 ?
                  <Button variant="outlined" className={'center-element'} onClick={startGame}>Start Game</Button> : ''}
            </div>
        }
        <RoomCode code={props.room.roomCode}/>
      </div>
  );
};