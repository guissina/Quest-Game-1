import AvatarCard, { type AvatarProps } from "../AvatarCard";

type AvatarGridProps = {
  avatars: AvatarProps[];
};

const AvatarGrid: React.FC<AvatarGridProps> = ({ avatars }) => {
  return (
    <div className="avatar-grid">
      {avatars.map((avatar) => (
        <AvatarCard
          key={avatar.name}
          image={avatar.image}
          name={avatar.name}
          link={avatar.link}
          onClick={avatar.onClick}
        />
      ))}
    </div>
  );
};

export default AvatarGrid;
