--- выбрать все книги (не экземпляры книг) одного автора
select b.name,b.publish_year,a.name from library.authors a
    inner join library.book_authors ba on a.id = ba.author_id
                  left outer join library.books b on ba.book_id = b.id
            where a.name = 'Анджей Сапковский'

-- выбрать все книги (не экземпляры книг) одного автора, написанные в соавторстве с другими авторами
-- select author_id from library.book_authors group by author_id having count(author_id) > 1
select b.name,b.publish_year,a.name
from (select ba.book_id,
             ba.author_id,
             count(ba.author_id) over (partition by ba.book_id) as cnt_author
      from library.book_authors ba) as ma
         left outer join library.books b on b.id = ma.book_id
         inner join library.authors a on a.id = ma.author_id
where ma.cnt_author > 1 order by a.name