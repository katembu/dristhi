class Row
  def initialize(csv_row)
    @csv_row = csv_row
    @defaults = {}
  end

  def default_value field_header, default
    raise "Cannot find field with header: #{field_header} in this CSV. Are you using the right CSV?" if not @csv_row.header? field_header
    add_field field_header, default
  end

  def add_field field_name, default
    @defaults[field_name] = default
  end

  def field(header)
    value_from_row = @csv_row.field(header)
    return @defaults[header] if value_from_row.nil? or value_from_row.empty?
    return value_from_row
  end

  def [](header)
    field(header)
  end
end
