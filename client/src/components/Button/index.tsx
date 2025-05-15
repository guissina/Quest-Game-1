import React from 'react';
import './button.scss';

type ButtonProps = {
  children: React.ReactNode;
  onClick?: () => void;
  type?: 'button' | 'submit' | 'reset';
  className?: string;
  variant?: 'login' | 'secondary' | 'danger';
};

export const GenericButton: React.FC<ButtonProps> = ({
  children,
  onClick,
  type = 'button',
  className = '',
  variant = 'login',
}) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className={`button ${variant} ${className}`}
    >
      {children}
    </button>
  );
};

export default GenericButton;
