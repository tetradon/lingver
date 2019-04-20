UPDATE profile_translation
SET last_repeat_date = to_timestamp(0)
WHERE last_repeat_date is null;