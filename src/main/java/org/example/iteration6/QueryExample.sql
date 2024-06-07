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
select u.*
from bk
         inner join library.product_unit u on u.book_id = bk.book_id
where array ['Вадим Шефнер','Терри Пратчетт'] <@ bk.ats
  and u.inactive_date is null
  --and u.rent_id is null
  and not exists(select from library.rent_log r where r.product_id = u.id and status != 'Возвращено');

--  выбрать все экземпляры книг, списанные за последний квартал
 select b.name, u.*
from library.product_unit u
         inner join library.books b on b.id = u.book_id
where u.inactive_date >= current_date - interval '3 months';

--  выбрать 10 наиболее популярных книг за последний квартал с сортировкой по популярности
select count(b.name) as popular,
       max(b.name)
--     count(b.id) over (partition by b.id) as popular,b.name,u.shelf_position,r.*
from library.rent_log r
         left join library.product_unit u on r.product_id = u.id
         inner join library.books b on b.id = u.book_id
where r.start_date >= current_date - interval '3 months'
group by u.book_id
order by popular desc
limit 10;


-- выбрать по 10 наиболее популярных книг по всем категориям за последний квартал с сортировкой по популярности
EXPLAIN (FORMAT YAML) select count(b.id) as pop_bc, b.id, max(b.name), b.type as bk_name
from library.rent_log r
         inner join library.product_unit pu on pu.id = r.product_id
         left join library.books b on b.id = pu.book_id
where r.start_date >= current_date - interval '3 months'
group by b.type, b.id
order by b.type, pop_bc desc;
--тут не уверена позже посмотреть


-- выбрать 10 наиболее популярных авторов за последний квартал с сортировкой по популярности
select count(ba.author_id) as popul,
       max(a.name)         as aut_id
-- pu.book_id,ba.author_id,r.start_date,r.till_date,r.status
from library.rent_log r
         inner join library.product_unit pu on pu.id = r.product_id
         left join library.book_authors ba on ba.book_id = pu.book_id
         left join library.authors a on a.id = ba.author_id
where r.start_date >= current_date - interval '3 months'
group by author_id
order by popul desc
limit 10;

-- выбрать 10 наиболее популярных авторов за последний квартал с сортировкой по популярности
-- и отображением до трёх самых популярных книг по каждому автору
-- комментарии к нижнему
--- (популярность - наиболее часто экземпляры его книг(включая соавторство) берут в аренду)
--- (3 САМЫЕ поплуярные книги а не поплуярные за квартал - поэтому отдельным селектом популярные книги)
with popular_rent as (select r.id   as rent_id,
                             pu.id  as unit_id,
                             pu.book_id,
                             a.name,
                             b.name as bk_name,
                             ba.author_id,
                             r.start_date,
                             r.till_date,
                             r.status
                      from library.rent_log r
                               left join library.product_unit pu on pu.id = r.product_id
                               left join library.book_authors ba on ba.book_id = pu.book_id
                               left join library.authors a on a.id = ba.author_id
                               left join library.books b on b.id = pu.book_id),
     popul_author as (select count(t.author_id) as popul,
                             max(t.author_id)   as aut_id,
                             max(t.name)        as auth_name
                      from popular_rent t
                      where t.start_date >= current_date - interval '3 months'
                      group by t.author_id
                      limit 10),
     popul_books as (select count(book_id) as pop_bc, book_id, max(t.bk_name) as bk_name
                     from popular_rent t
                     group by book_id
                     order by pop_bc desc),
     pop_by_author as (select author_id, pb.book_id, pop_bc, pb.bk_name as bk_name
                       from library.book_authors ba
                                inner join popul_books pb on ba.book_id = pb.book_id
                       order by pop_bc)
select pa.auth_name,
       ARRAY(select pba1.bk_name
             from pop_by_author as pba1
             where pba1.author_id = pa.aut_id
             order by pba1.pop_bc desc
             limit 3) as res
from popul_author pa
order by popul desc;

-- выбрать 100 наиболее активных читателей (критерии придумать самостоятельно)
-- чаще всего берут в аренду книги и возвращают не нарушив срок
select count(client_id) cnt_cl_per_rent, max(c.first_name) as fst_name, max(c.last_name) as lst_name
from library.rent_log r
         inner join library.client_card cc on cc.id = r.rent_by_id
         left join library.clients c on c.id = cc.client_id
where r.status = 'Возвращено'
   or (r.status = 'Отдано' and r.till_date >= current_date)
group by client_id
order by cnt_cl_per_rent desc
limit 100;

-- выбрать 100 наиболее безответственных читателей, у которых больше всех просроченных возвратов
-- выбрать 100 наиболее безответственных читателей, у которых больше всех утерь книг (оба условия один запрос условие поменять)
select count(client_id) cnt_cl_per_rent, max(c.first_name) as fst_name, max(c.last_name) as lst_name
from library.rent_log r
         inner join library.client_card cc on cc.id = r.rent_by_id
         left join library.clients c on c.id = cc.client_id
where r.status = 'Потеряно'
   or (r.status = 'Отдано' and r.till_date < current_date)
group by client_id
order by cnt_cl_per_rent desc
limit 100;

-- выбрать количество выдач (возвратов) книг по дням за последний месяц
with rent_month as (select *
                    from library.rent_log r
                    where r.start_date >= current_date - interval '1 months')
select start_date as event_day, count(id), 'Отдано' as stat
from rent_month t
where t.status = 'Отдано'
group by start_date
union all
select return_date as event_day, count(id), 'Возвращено' as stat
from rent_month t
where t.status = 'Возвращено'
group by return_date;

--- выбрать самые пустующие полки / стеллажи

-- пустующие полки
select count(current_unit) as cnt_unit, bookshelf_id,type
from library.shelf_position
         inner join library.bookcase_shelfs sh on bookshelf_id = sh.id
group by bookshelf_id,type
order by cnt_unit;

-- пустующие стеллажи - на которых меньше всего юнитов(экземпляры книг\продукции) лежит
select count(current_unit) as cnt_unit, bookcase_num
from library.shelf_position
         inner join library.bookcase_shelfs sh on bookshelf_id = sh.id
group by sh.bookcase_num
order by cnt_unit;

--- посчитать количество свободных мест для книг
select count(1)
from library.shelf_position
where current_unit is null;

--- посчитать количество всех книг / экземпляров книг / авторов / книг по каждому автору / экзмпляров книг по каждому автору /
select count(1) as cnt_book,'Количествр книг' from library.books
union all
select count(1) as cnt_book,'Количество экземпляров книг' from library.product_unit;

-- select count(1) as cnt_book,'Количествр книг по каждому автору' from library.books
-- select count(1) as cnt_book,'Количествр книг' from library.books

--and (r.return_date is not null or r.till_date >= current_date)
-- select * from library.book_authors inner join library.books b2 on b2.id = book_authors.book_id

--update library.rent_log set return_date = null where status='Отдано'
-- select res.auth_name,res.arr[1:3] from (
-- select popul,
--        b2.name,
--        array_agg(b2.name) over (partition by author_id order by pop_bc) as arr,
--        aut_id,
--        auth_name,
--        pb.pop_bc
-- from popul_author a
--          inner join library.book_authors ab on a.aut_id = ab.author_id
--          inner join library.books b2 on b2.id = ab.book_id
--          inner join popul_books pb on pb.book_id = b2.id
-- order by popul desc
-- ) as res
--  group by author_id
--select res.arr[1:3],res.author_id from (


--
-- popul_book as (select * popular_rent)
-- select *
-- from popul_author r
--          left join popular_rent t on t.author_id = r.aut_id

-- select distinct array_agg(bk_name) over (partition by author_id),name from popular_rent
-- select max(l.popul),max(author_id),max(auth_name),array_agg(bk_name)
-- from popul_author l left join popular_rent on l.aut_id = popular_rent.author_id
-- group by bk_name,l.popul
-- order by popul desc
