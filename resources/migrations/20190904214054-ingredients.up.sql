create table ingredient
(
	id           serial primary key,
	name         varchar(255) not null,
	display      varchar(255) not null,
	protein      integer not null,
	carbohydrate integer not null,
	fat          integer not null
);

--;;
comment on column "ingredient"."name" is 'the canonical and URL-safe name for a food e.g white-rice';
--;;
comment on column "ingredient"."display" is 'the name displayed for a food in the frontend e.g White Rice';
--;;
comment on column "ingredient"."protein" is 'amount of protein per 100g stored as mg e.g 30000 is 30g';
