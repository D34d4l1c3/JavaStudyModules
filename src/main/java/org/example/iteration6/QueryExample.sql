--- выбрать все книги (не экземпляры книг) одного автора
select b.name, b.publish_year, a.name
from library.authors a
         inner join library.book_authors ba on a.id = ba.author_id
         left outer join library.books b on ba.book_id = b.id
where a.name = 'Анджей Сапковский';

-- выбрать все книги (не экземпляры книг) одного автора, написанные в соавторстве с другими авторами
select b.name, b.id
from library.books b
         inner join library.book_authors p on b.id = p.book_id
         inner join library.authors a on a.id = p.author_id
where a.name = 'Ольга Громыко'
  and p.book_id in (select t.book_id
                    from library.book_authors t
                    where t.author_id != (select id from library.authors where name = a.name));
-- Баловство по запросу аналогичному но усложненная фигня
-- select *
-- from (select ba.book_id,
--              b.name,
--              ba.author_id,
--              a.name,
--              count(ba.author_id) over (partition by ba.book_id) as cnt_author
--       from library.book_authors ba
--                left outer join library.authors a on a.id = ba.author_id
--                left outer join library.books b on b.id = ba.book_id
--       where exists(select 1
--                    from library.book_authors ra
--                             inner join library.authors a2 on a2.id = ra.author_id
--                    where ra.book_id = ba.book_id
--                      and a2.name = 'Ольга Громыко')) as rep
-- where rep.cnt_author > 1;


-- выбрать все экземпляры определённой книги (по названию и автору/авторам), которые есть в библиотеке (не выданы и не списаны)
with bk as (select array_agg(a.name) as ats, max(b.id) as book_id
      from library.books b
               inner join library.book_authors ba on b.id = ba.book_id
               inner join library.authors a on a.id = ba.author_id
      where b.name = 'Терри Пратчетт. Правда')
select u.* from bk inner join library.product_unit u on u.book_id = bk.book_id
         where array ['Вадим Шефнер','Терри Пратчетт'] <@ bk.ats
           and u.inactive_date is null
           --and u.rent_id is null
           and not exists(select from library.rent_log r where r.product_id=u.id and status != 'Возвращено');


--  выбрать все экземпляры книг, списанные за последний квартал
select b.name,u.*
from library.product_unit u inner join library.books b on b.id = u.book_id
where u.inactive_date >= current_date - interval '3 months';

--  выбрать 10 наиболее популярных книг за последний квартал с сортировкой по популярности
select
    count(b.name) as popular,max(b.name)
--     count(b.id) over (partition by b.id) as popular,b.name,u.shelf_position,r.*
from library.rent_log r
         left join library.product_unit u on r.product_id = u.id
         inner join library.books b on b.id = u.book_id
where r.start_date >= current_date - interval '3 months'
group by u.book_id
order by popular desc limit 10;