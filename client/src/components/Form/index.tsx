import React, { useState } from "react";
import GenericButton from "../Button";

type Field = {
  name: string;
  label?: string;
  type?: string;
  placeholder?: string;
  required?: boolean;
  defaultValue?: string;
};

type GenericFormProps = {
  fields: Field[];
  submitLabel?: React.ReactNode;
  variant?: "login" | "secondary" | "danger"
  className?: string;
  method: "POST" | "GET"
};

const GenericForm: React.FC<GenericFormProps> = ({
  fields,
  submitLabel = "Enviar",
  variant,
  method,
}) => {
  const [values, setValues] = useState<Record<string, string>>(() => {
    const initial: Record<string, string> = {};
    fields.forEach(({ name, defaultValue }) => {
      initial[name] = defaultValue || "";
    });
    return initial;
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setValues((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <form method={method}>
      {fields.map(({ name, label, type = "text", placeholder, required, ...rest }) => (
        <div key={name}>
          {label && (
            <label htmlFor={name}>
              {label}
            </label>
          )}
          <input
            id={name}
            name={name}
            type={type}
            placeholder={placeholder}
            required={required}
            value={values[name]}
            onChange={handleChange}
            {...rest}
            
          />
        </div>
      ))}

      <GenericButton variant={variant} type="submit">
        {submitLabel}
      </GenericButton>
    </form>
  );
};

export default GenericForm;
