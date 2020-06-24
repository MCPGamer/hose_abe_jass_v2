import React from 'react';
import './CardComponent.css';
import {Card} from '../models/card';

type Props = {
  card: Card;
  handleSwap: (card: Card) => void;
  selected: boolean;
}

export const CardComponent: React.FC<Props> = (props) => {
  const handleSwap = () => {
    props.handleSwap(props.card);
  };

  return (
      <div>
        {
          props.card === null ? '' :
              (<img className={props.selected ? 'card selected' : 'card'}
                    src={`${process.env.PUBLIC_URL}/img/jasskarten/${props.card.cardColor}${props.card.cardValue}.gif`}
                    alt={'Karte'} onClick={handleSwap}/>)
        }
      </div>
  );
};
