export type AvatarProps = {
  onClick?: () => void;
  image: string;
  name: string;
};

export const AvatarCard: React.FC<AvatarProps> = ({
  onClick,
  image,
  name,
}) => {
  return (
    <div className="avatar-container">
      <div className="avatar">
        <button onClick={onClick}>
          <img src={image} alt={`Imagem ${name}`} />
        </button>
      </div>
    </div>
  );
};

export default AvatarCard;
