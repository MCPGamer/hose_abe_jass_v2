import React from 'react';
import './CardHand.css';
import {Card} from '../models/card';
import {CardComponent} from './CardComponent';

type Props = {
    cards: Card[];
    handleSwap: (card:Card) => void;
    enabled:boolean;
    selectedCard?:Card;
}

export const CardHand: React.FC<Props> = (props) => {
    let index = 0;

    const handleSwap = (card:Card) => {
        if(props.enabled){
            props.handleSwap(card);
        }
    };

    return (
        <div className={'inline'}>
          {props.cards.map((c) => {
              index = index + 1;
              return <CardComponent key={index} card={c} handleSwap={handleSwap} selected={c === props.selectedCard}/>
          })}
        </div>
    );
};
